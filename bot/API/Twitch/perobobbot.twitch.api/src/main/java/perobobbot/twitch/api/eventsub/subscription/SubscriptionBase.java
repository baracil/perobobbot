package perobobbot.twitch.api.eventsub.subscription;

import perobobbot.twitch.api.eventsub.TwitchSubscriptionRequest;

public abstract class SubscriptionBase implements Subscription {


    @Override
    public TwitchSubscriptionRequest.Builder completeRequest(TwitchSubscriptionRequest.Builder builder) {
        return builder.version(getVersion())
                      .type(getType())
                      .condition(getCondition());
    }
}
