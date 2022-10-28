package perobobbot.twitch.web.api.eventsub.event;

import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.api.TwitchApiPayload;
import perobobbot.twitch.web.api.UserInfo;

@Value
public class ChannelUnbanEvent implements BroadcasterProvider, EventSubEvent, TwitchApiPayload {
    @NonNull UserInfo user;
    @NonNull UserInfo broadcaster;
    @NonNull UserInfo moderator;
}
