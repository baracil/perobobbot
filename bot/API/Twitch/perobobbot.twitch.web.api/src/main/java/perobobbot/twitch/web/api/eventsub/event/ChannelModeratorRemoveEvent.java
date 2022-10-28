package perobobbot.twitch.web.api.eventsub.event;

import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.web.api.UserInfo;

@Value
public class ChannelModeratorRemoveEvent implements BroadcasterProvider, EventSubEvent{

    @NonNull UserInfo broadcaster;
    @NonNull UserInfo user;
}
