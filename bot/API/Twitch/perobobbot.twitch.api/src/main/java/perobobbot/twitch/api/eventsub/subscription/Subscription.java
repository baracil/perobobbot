package perobobbot.twitch.api.eventsub.subscription;

import lombok.NonNull;
import perobobbot.twitch.api.Conditions;
import perobobbot.twitch.api.SubscriptionType;
import perobobbot.twitch.api.eventsub.TwitchSubscriptionRequest;

public interface Subscription {

    @NonNull SubscriptionType getType();

    @NonNull Conditions getCondition();

    default @NonNull String getVersion() {
        return getType().getVersion();
    }

    TwitchSubscriptionRequest.@NonNull Builder completeRequest(TwitchSubscriptionRequest.@NonNull Builder builder);

}
