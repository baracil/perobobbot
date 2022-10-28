package perobobbot.twitch.web.api.eventsub.event;

import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.api.TwitchApiPayload;
import perobobbot.twitch.web.api.UserInfo;

import java.time.Instant;

@Value
public class ChannelFollowEvent implements BroadcasterProvider, EventSubEvent, TwitchApiPayload {

    @NonNull UserInfo user;
    @NonNull UserInfo broadcaster;
    @NonNull Instant followedAt;

}
