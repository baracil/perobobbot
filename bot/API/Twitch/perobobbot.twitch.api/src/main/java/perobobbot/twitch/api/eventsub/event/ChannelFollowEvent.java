package perobobbot.twitch.api.eventsub.event;

import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.api.TwitchApiPayload;
import perobobbot.twitch.api.UserInfo;

import java.time.Instant;

@Value
@Serdeable
public class ChannelFollowEvent implements BroadcasterProvider, EventSubEvent, TwitchApiPayload {

    @NonNull UserInfo user;
    @NonNull UserInfo broadcaster;
    @NonNull Instant followedAt;

}
