package perobobbot.twitch.eventsub.sync;

import fpc.tools.fp.Nil;
import fpc.tools.lang.ThrowableTool;
import io.micronaut.scheduling.annotation.Scheduled;
import jakarta.inject.Singleton;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import perobobbot.api.SubscriptionView;
import perobobbot.service.api.SubscriptionService;
import perobobbot.service.api.UpdateSubscriptionParameters;
import perobobbot.twitch.api.Conditions;
import perobobbot.twitch.api.Twitch;
import perobobbot.twitch.api.eventsub.TwitchSubscription;
import perobobbot.twitch.api.eventsub.subscription.GenericSubscription;
import perobobbot.twitch.service.api.EventSubService;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@Singleton
@RequiredArgsConstructor
@Slf4j
public class SubscriptionSynchronizer {

    private final @NonNull EventSubService eventSubService;
    private final @NonNull SubscriptionService subscriptionService;

    @Scheduled(initialDelay = "10s", fixedDelay = "600s")
    public void synchronize() {
        try {
            doSynchronize();
        } catch (Throwable t) {
            ThrowableTool.interruptIfCausedByInterruption(t);
            LOG.warn("Fail to synchronize SubEventNotifications");
        }
    }

    private void doSynchronize() {
        final var references = subscriptionService.listSubscriptions(Twitch.PLATFORM);
        final var onTwitch = eventSubService.getSubscriptions();

        final var match = Matcher.match(onTwitch, references);

        if (!match.hasAnyChange()) {
            return;
        }
        LOG.info("Synchronize Subscriptions : Rf:{} Up:{} Rk:{}", match.getToRefreshSubs().size(), match.getToUpdateSubs().size(), match.getToRevokeSubs().size());


        var action = Stream.of(match.getToUpdateSubs().entrySet().stream()
                                    .map(this::subscriptionUpdater),
                                   match.getToRevokeSubs()
                                        .stream()
                                        .map(this::subscriptionRevoker),
                                   match.getToRefreshSubs()
                                        .stream()
                                        .map(this::performRefresh)
                           ).flatMap(s -> s)
                           .map(this::toCompletableFuture)
                           .reduce((c1, c2) -> c1.thenCombine(c2, (a, b) -> a))
                           .orElse(null);
        if (action!=null) {
            action.whenComplete(this::handleActionResult);
        }
    }

    private void handleActionResult(Nil r, Throwable t) {
        if (t != null) {
            LOG.warn("Synchronization failed",t);
        }
    }

    private @NonNull Runnable performRefresh(@NonNull SubscriptionView subscriptionViewToRefresh) {
        final var subscriptionType = subscriptionViewToRefresh.getSubscriptionType();
        final var conditions = subscriptionViewToRefresh.getConditions();
        final var subscriptionDbId = subscriptionViewToRefresh.getId();
        final var subscription = GenericSubscription.from(subscriptionType, Conditions.fromMap(conditions));
        return () -> {
            final var sub = eventSubService.createSubscription(subscription);
            subscriptionService.updateSubscription(subscriptionDbId, createUpdateParameters(sub));
        };
    }


    private @NonNull Runnable subscriptionUpdater(@NonNull Map.Entry<Long, TwitchSubscription> updateInfo) {
        final var subscriptionId = updateInfo.getKey();
        final var parameter = createUpdateParameters(updateInfo.getValue());
        return () -> subscriptionService.updateSubscription(subscriptionId, parameter);
    }

    private @NonNull Runnable subscriptionRevoker(@NonNull String subscriptionId) {
        return () -> eventSubService.deleteSubscription(subscriptionId);
    }


    private @NonNull CompletableFuture<Nil> toCompletableFuture(@NonNull Runnable runnable) {
        return CompletableFuture.supplyAsync(() -> {
            runnable.run();
            return Nil.NULL;
        });
    }

    private boolean existsOnTwitch(@NonNull TwitchSubscription status) {
        return !status.getStatus().isFailure();
    }

    private @NonNull UpdateSubscriptionParameters createUpdateParameters(@NonNull TwitchSubscription twitchSubscription) {
        return new UpdateSubscriptionParameters(twitchSubscription.getSubscriptionId(), twitchSubscription.getCallbackUrl());
    }

}
