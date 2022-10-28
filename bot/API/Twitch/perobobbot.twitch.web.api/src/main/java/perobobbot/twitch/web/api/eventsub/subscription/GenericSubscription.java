package perobobbot.twitch.web.api.eventsub.subscription;

import fpc.tools.lang.IdentifiedEnumTools;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.twitch.web.api.Conditions;
import perobobbot.twitch.web.api.SubscriptionType;

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
