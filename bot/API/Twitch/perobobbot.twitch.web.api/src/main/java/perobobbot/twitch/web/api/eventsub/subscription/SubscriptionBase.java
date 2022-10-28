package perobobbot.twitch.web.api.eventsub.subscription;

import lombok.NonNull;
import perobobbot.twitch.web.api.eventsub.TwitchSubscriptionRequest;

public abstract class SubscriptionBase implements Subscription {


    @Override
    public TwitchSubscriptionRequest.@NonNull Builder completeRequest(TwitchSubscriptionRequest.@NonNull Builder builder) {
        return builder.version(getVersion())
                      .type(getType())
                      .condition(getCondition());
    }
}
