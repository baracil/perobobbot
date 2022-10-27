package perobobbot.twitch.api.eventsub.subscription;

import fpc.tools.lang.IdentifiedEnumTools;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.twitch.api.Conditions;
import perobobbot.twitch.api.SubscriptionType;

@RequiredArgsConstructor
@Getter
public class GenericSubscription extends SubscriptionBase {

    public static GenericSubscription from(@NonNull String subscriptionType, @NonNull Conditions conditions) {
        final var type = IdentifiedEnumTools.getEnum(subscriptionType, SubscriptionType.class);
        return new GenericSubscription(type,conditions);
    }

    private final @NonNull SubscriptionType type;
    private final @NonNull Conditions condition;
}
