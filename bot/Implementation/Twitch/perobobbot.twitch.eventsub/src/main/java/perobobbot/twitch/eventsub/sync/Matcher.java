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


    private Map<Key, List<TwitchSubscription>> validOnPlatformPerKey;
    private Map<Key, SubscriptionView> onBotPerKey;

    private Match.MatchBuilder builder = Match.builder();


    private @NonNull Match match() {
//        this.display();
        this.dispatchPerKey();
        this.checkAllKeys();
        this.handleInvalids();
        return builder.build();
    }

    private void display() {
        System.out.println("ON PLATFORM");
        onPlatform.forEach(s -> System.out.println(s.getSubscriptionId()+ "  " + s.getCallbackUrl()));
        System.out.println("ON BOT");
        onBot.forEach(s -> System.out.println(s.getSubscriptionId() + "  "+s.getCallbackUrl()));
    }

    private void handleInvalids() {
        onPlatform.stream()
                  .filter(not(this::isValid))
                  .forEach(v -> builder.toRevokeSub(v.getSubscriptionId()));
    }

    private boolean isValid(@NonNull TwitchSubscription twitchSubscription) {
        return twitchSubscription.getStatus() == SubscriptionStatus.ENABLED;
    }

    private void dispatchPerKey() {
        this.validOnPlatformPerKey = onPlatform.stream()
                                               .filter(this::isValid)
                                               .collect(Collectors.groupingBy(Key::from));

        this.onBotPerKey = onBot.stream()
                                .collect(Collectors.toMap(Key::from, s -> s));
    }

    private void checkAllKeys() {
        Stream.concat(
                validOnPlatformPerKey.keySet().stream(),
                onBotPerKey.keySet().stream()
        ).distinct().forEach(this::checkForOneData);
    }

    private void checkForOneData(@NonNull Key key) {
        final var listOfValidOnPlatform = validOnPlatformPerKey.getOrDefault(key,List.of());
        final var onBot = onBotPerKey.get(key);

        if (listOfValidOnPlatform.isEmpty()) {
            if (onBot != null) {
                builder.toRefreshSub(onBot);
            }
            return;
        }

        if (onBot == null) {
            listOfValidOnPlatform.stream()
             .map(TwitchSubscription::getSubscriptionId)
             .forEach(builder::toRevokeSub);
            return;
        }

        final Predicate<TwitchSubscription> sameIdPredicate = s -> s.getSubscriptionId().equals(onBot.getSubscriptionId());

        final boolean match = listOfValidOnPlatform.stream().anyMatch(sameIdPredicate);
        if (match) {
            listOfValidOnPlatform.stream()
             .filter(not(sameIdPredicate))
             .map(TwitchSubscription::getSubscriptionId)
             .forEach(builder::toRevokeSub);
        } else {
            builder.toUpdateSub(onBot.getId(), listOfValidOnPlatform.get(0));
            listOfValidOnPlatform.stream()
             .skip(1)
             .map(TwitchSubscription::getSubscriptionId)
             .forEach(builder::toRevokeSub);
        }

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
