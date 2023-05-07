package perobobbot.twitch.api.eventsub.event;

import io.micronaut.serde.annotation.Serdeable;
import lombok.Value;
import perobobbot.twitch.api.TwitchApiPayload;
import perobobbot.twitch.api.UserInfo;

@Value
@Serdeable
public class ChannelModeratorRemoveEvent implements BroadcasterProvider, EventSubEvent, TwitchApiPayload {

    UserInfo broadcaster;
    UserInfo user;
}
