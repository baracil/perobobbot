package perobobbot.twitch.eventsub.sync;

import com.google.common.collect.ImmutableList;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import perobobbot.api.SubscriptionData;
import perobobbot.api.SubscriptionView;
import perobobbot.twitch.api.eventsub.SubscriptionStatus;
import perobobbot.twitch.api.eventsub.TwitchSubscription;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.function.Predicate.not;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Matcher {

    public static @NonNull Match match(
            @NonNull ImmutableList<TwitchSubscription> onPlatform,
            @NonNull ImmutableList<SubscriptionView> persisted
    ) {
        return new Matcher(onPlatform, persisted).match();
    }

    private final @NonNull ImmutableList<TwitchSubscription> onPlatform;
    private final @NonNull ImmutableList<SubscriptionView> onBot;


    private Map<Key, List<TwitchSubscription>> onPlatformPerKey;
    private Map<Key, SubscriptionView> onBotPerKey;

    private Match.MatchBuilder builder = Match.builder();


    private @NonNull Match match() {
        this.dispatchPerKey();
        this.checkAllKeys();
        this.handleInvalids();
        return builder.build();
    }

    private void dispatchPerKey() {
        this.onPlatformPerKey = onPlatform.stream().collect(Collectors.groupingBy(Key::from));
        this.onBotPerKey = onBot.stream().collect(Collectors.toMap(Key::from, s -> s));
    }

    private boolean isValid(@NonNull TwitchSubscription twitchSubscription) {
        return twitchSubscription.getStatus() == SubscriptionStatus.ENABLED;
    }

    private void checkAllKeys() {
        Stream.concat(
                onPlatformPerKey.keySet().stream(),
                onBotPerKey.keySet().stream()
        ).distinct().forEach(this::checkForOneKey);
    }

    private void handleInvalids() {
        onPlatform.stream()
                  .filter(not(this::isValid))
                  .forEach(v -> builder.toRevokeSub(v.getSubscriptionId()));
    }

    private void display() {
        System.out.println("ON PLATFORM");
        onPlatform.forEach(s -> System.out.println(s.getSubscriptionId() + "  " + s.getCallbackUrl()));
        System.out.println("ON BOT");
        onBot.forEach(s -> System.out.println(s.getSubscriptionId() + "  " + s.getCallbackUrl()));
    }


    private void checkForOneKey(@NonNull Key key) {
        final var onPlatform = onPlatformPerKey.getOrDefault(key, List.of());
        final var onBot = onBotPerKey.get(key);

        if (onPlatform.isEmpty() && onBot == null) {
            return;
        }

        if (onPlatform.isEmpty()) {
            if (onBot.hasPlatformId()) {
                builder.toRefreshSub(onBot);
            } else {
                builder.toCreate(onBot);
            }
            return;
        }

        if (onBot == null) {
            onPlatform.stream()
                      .filter(this::isRevokable)
                      .map(TwitchSubscription::getSubscriptionId)
                      .forEach(builder::toRevokeSub);
            return;
        }

        final Predicate<TwitchSubscription> sameIdPredicate = s -> s.getSubscriptionId().equals(onBot.getSubscriptionId());

        final boolean match = onPlatform.stream().anyMatch(sameIdPredicate);
        if (match) {
            onPlatform.stream()
                      .filter(not(sameIdPredicate))
                      .filter(this::isRevokable)
                      .map(TwitchSubscription::getSubscriptionId)
                      .forEach(builder::toRevokeSub);
        } else {
            builder.toUpdateSub(onBot.getId(), onPlatform.get(0));
            onPlatform.stream()
                      .skip(1)
                      .filter(this::isRevokable)
                      .map(TwitchSubscription::getSubscriptionId)
                      .forEach(builder::toRevokeSub);
        }

    }

    private boolean isRevokable(TwitchSubscription subscription) {
        return switch (subscription.getStatus()) {
            case ENABLED, NOTIFICATION_FAILURES_EXCEEDED, WEBHOOK_CALLBACK_VERIFICATION_FAILED, WEBHOOK_CALLBACK_VERIFICATION_PENDING ->
                    true;
            case AUTHORIZATION_REVOKED, USER_REMOVED -> false;
        };
    }

    @Value
    private static class Key {

        public static @NonNull Key from(@NonNull TwitchSubscription twitchSubscription) {
            return new Key(createData(twitchSubscription), twitchSubscription.getCallbackUrl());
        }

        public static @NonNull Key from(@NonNull SubscriptionView subscriptionView) {
            return new Key(createData(subscriptionView), subscriptionView.getCallbackUrl());
        }

        @NonNull SubscriptionData subscriptionData;
        @NonNull String callbackUrl;
    }

    private static @NonNull SubscriptionData createData(@NonNull TwitchSubscription twitchSubscription) {
        return new SubscriptionData(
                twitchSubscription.getPlatform(),
                twitchSubscription.getSubscriptionType(),
                twitchSubscription.getConditions().toMap()
        );
    }

    private static @NonNull SubscriptionData createData(@NonNull SubscriptionView subscriptionView) {
        return new SubscriptionData(
                subscriptionView.getPlatform(),
                subscriptionView.getSubscriptionType(),
                subscriptionView.getConditions()
        );
    }

}
