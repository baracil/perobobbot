package perobobbot.twitch.web.api.eventsub.subscription;

import fpc.tools.lang.IdentifiedEnumTools;
import lombok.NonNull;
import perobobbot.twitch.api.Twitch;
import perobobbot.twitch.web.api.Conditions;
import perobobbot.twitch.web.api.SubscriptionType;
import perobobbot.twitch.web.api.eventsub.TwitchSubscriptionRequest;

public interface Subscription {

    @NonNull SubscriptionType getType();

    @NonNull Conditions getCondition();

    default @NonNull String getVersion() {
        return getType().getVersion();
    }

    TwitchSubscriptionRequest.@NonNull Builder completeRequest(TwitchSubscriptionRequest.@NonNull Builder builder);


    default @NonNull perobobbot.api.Subscription toCommon() {
        return new perobobbot.api.Subscription(
                Twitch.PLATFORM,
                getType().getIdentification(),
                getCondition().toMap()
        );
    }

    static @NonNull Subscription fromCommon(@NonNull perobobbot.api.Subscription subscription) {
        return IdentifiedEnumTools.getEnum(subscription.getType(), SubscriptionType.class)
                                  .create(Conditions.fromMap(subscription.getData()));
    }
}
