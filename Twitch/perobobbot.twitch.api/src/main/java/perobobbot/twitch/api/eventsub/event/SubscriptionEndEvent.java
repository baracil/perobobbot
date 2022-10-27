package perobobbot.twitch.api.eventsub.event;

import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.api.UserInfo;

@Value
public class SubscriptionEndEvent implements BroadcasterProvider, EventSubEvent {
    @NonNull UserInfo user;
    @NonNull UserInfo broadcaster;

}
