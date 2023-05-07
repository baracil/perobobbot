package perobobbot.twitch.api.eventsub.subscription;

import perobobbot.twitch.api.Conditions;
import perobobbot.twitch.api.SubscriptionType;
import perobobbot.twitch.api.eventsub.TwitchSubscriptionRequest;

public interface Subscription {

    SubscriptionType getType();

    Conditions getCondition();

    default String getVersion() {
        return getType().getVersion();
    }

    TwitchSubscriptionRequest.Builder completeRequest(TwitchSubscriptionRequest.Builder builder);

}
