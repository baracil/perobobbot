package perobobbot.twitch.api.eventsub.event;

import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.api.UserInfo;

@Value
public class ChannelModeratorRemoveEvent implements BroadcasterProvider, EventSubEvent{

    @NonNull UserInfo broadcaster;
    @NonNull UserInfo user;
}
