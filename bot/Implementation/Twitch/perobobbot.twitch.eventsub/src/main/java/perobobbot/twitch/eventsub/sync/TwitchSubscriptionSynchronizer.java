package perobobbot.twitch.eventsub.sync;

import fpc.tools.fp.Nil;
import io.micronaut.scheduling.annotation.Async;
import jakarta.inject.Singleton;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import perobobbot.api.SubscriptionView;
import perobobbot.api.bus.BusEventListener;
import perobobbot.api.bus.PlatformSync;
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
public class TwitchSubscriptionSynchronizer {

    private final @NonNull EventSubService eventSubService;
    private final @NonNull SubscriptionService subscriptionService;

    @BusEventListener
    @Async
    public void platformSynListener(@NonNull PlatformSync platformSync) {
        if (platformSync.appliesTo(Twitch.PLATFORM)) {
            synchronize();
        }
    }

    private void synchronize() {
        final var references = subscriptionService.listSubscriptionsOnPlatform(Twitch.PLATFORM);
        final var onTwitch = eventSubService.getSubscriptions();

        final var match = Matcher.match(onTwitch, references);

        if (!match.hasAnyChange()) {
            return;
        }
        LOG.info("Synchronize Subscriptions : Cr:{} Rf:{} Up:{} Rk:{}",
                match.getToCreates().size(), match.getToRefreshSubs().size(),
                match.getToUpdateSubs().size(), match.getToRevokeSubs().size());


        Stream.of(
                      match.getToCreates().stream().map(this::subscriptionCreator),
                      match.getToUpdateSubs().entrySet().stream().map(this::subscriptionUpdater),
                      match.getToRevokeSubs().stream().map(this::subscriptionRevoker),
                      match.getToRefreshSubs().stream().map(this::subscriptionRefresher)
              ).flatMap(s -> s)
              .map(this::toCompletableFuture)
              .reduce((c1, c2) -> c1.thenCombine(c2, (a, b) -> a))
              .ifPresent(a -> a.whenComplete(this::handleActionResult));
    }


    private void handleActionResult(Nil r, Throwable t) {
        if (t != null) {
            LOG.warn("Synchronization failed", t);
        }
    }

    private @NonNull Runnable subscriptionCreator(SubscriptionView subscriptionView) {
        return this.subscriptionRefresher(subscriptionView);
    }

    private @NonNull Runnable subscriptionRefresher(@NonNull SubscriptionView subscriptionView) {
        final var subscriptionType = subscriptionView.getSubscriptionType();
        final var conditions = subscriptionView.getConditions();
        final var subscriptionDbId = subscriptionView.getId();
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

    private @NonNull UpdateSubscriptionParameters createUpdateParameters(@NonNull TwitchSubscription twitchSubscription) {
        return new UpdateSubscriptionParameters(twitchSubscription.getSubscriptionId(), twitchSubscription.getCallbackUrl());
    }

}
