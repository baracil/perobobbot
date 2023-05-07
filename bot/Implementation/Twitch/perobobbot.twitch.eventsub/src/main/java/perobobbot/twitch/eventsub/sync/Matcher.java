package perobobbot.twitch.eventsub.sync;

import lombok.AccessLevel;
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

    public static Match match(
            List<TwitchSubscription> onPlatform,
            List<SubscriptionView> persisted
    ) {
        return new Matcher(onPlatform, persisted).match();
    }

    private final List<TwitchSubscription> onPlatform;
    private final List<SubscriptionView> onBot;


    private Map<Key, List<TwitchSubscription>> onPlatformPerKey = Map.of();
    private Map<Key, SubscriptionView> onBotPerKey = Map.of();

    private final Match.MatchBuilder builder = Match.builder();


    private Match match() {
        this.dispatchPerKey();
        this.checkAllKeys();
        this.handleInvalids();
        return builder.build();
    }

    private void dispatchPerKey() {
        this.onPlatformPerKey = onPlatform.stream().collect(Collectors.groupingBy(Key::from));
        this.onBotPerKey = onBot.stream().collect(Collectors.toMap(Key::from, s -> s));
    }

    private boolean isValid(TwitchSubscription twitchSubscription) {
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


    private void checkForOneKey(Key key) {
        final var onPlatform = onPlatformPerKey.getOrDefault(key, List.of());
        final var onBot = onBotPerKey.get(key);

        if (onPlatform.isEmpty() && onBot == null) {
            return;
        }

        if (onPlatform.isEmpty()) {
            if (onBot.isEnabled()) {
                if (onBot.hasPlatformId()) {
                    builder.toRefreshSub(onBot);
                } else {
                    builder.toCreate(onBot);
                }
            }
            return;
        }


        if (onBot == null || !onBot.isEnabled()) {
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

        public static Key from(TwitchSubscription twitchSubscription) {
            return new Key(createData(twitchSubscription), twitchSubscription.getCallbackUrl());
        }

        public static Key from(SubscriptionView subscriptionView) {
            return new Key(createData(subscriptionView), subscriptionView.getCallbackUrl());
        }

        SubscriptionData subscriptionData;
        String callbackUrl;
    }

    private static SubscriptionData createData(TwitchSubscription twitchSubscription) {
        return new SubscriptionData(
                twitchSubscription.getPlatform(),
                twitchSubscription.getSubscriptionType(),
                twitchSubscription.getConditions().toMap()
        );
    }

    private static SubscriptionData createData(SubscriptionView subscriptionView) {
        return new SubscriptionData(
                subscriptionView.getPlatform(),
                subscriptionView.getSubscriptionType(),
                subscriptionView.getConditions()
        );
    }

}
