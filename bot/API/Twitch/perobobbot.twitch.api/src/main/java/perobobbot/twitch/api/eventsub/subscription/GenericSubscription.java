package perobobbot.twitch.api.eventsub.subscription;

import fpc.tools.lang.IdentifiedEnumTools;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import perobobbot.twitch.api.Conditions;
import perobobbot.twitch.api.SubscriptionType;

@RequiredArgsConstructor
@Getter
public class GenericSubscription extends SubscriptionBase {

    public static GenericSubscription from(String subscriptionType, Conditions conditions) {
        final var type = IdentifiedEnumTools.getEnum(subscriptionType, SubscriptionType.class);
        return new GenericSubscription(type,conditions);
    }

    private final SubscriptionType type;
    private final Conditions condition;
}
