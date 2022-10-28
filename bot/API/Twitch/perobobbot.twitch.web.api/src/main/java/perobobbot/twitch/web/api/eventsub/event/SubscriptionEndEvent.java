package perobobbot.twitch.web.api.eventsub.event;

import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.web.api.UserInfo;

@Value
public class SubscriptionEndEvent implements BroadcasterProvider, EventSubEvent {
    @NonNull UserInfo user;
    @NonNull UserInfo broadcaster;

}
