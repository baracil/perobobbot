package perobobbot.twitch.api.eventsub.event;

import io.micronaut.serde.annotation.Serdeable;
import io.micronaut.serde.config.naming.SnakeCaseStrategy;
import lombok.Value;
import perobobbot.twitch.api.TwitchApiPayload;
import perobobbot.twitch.api.UserInfo;
import perobobbot.twitch.api.serde.ISOInstantSerde;

import java.time.Instant;

@Value
@Serdeable(naming = SnakeCaseStrategy.class)
public class StreamOnlineEvent implements BroadcasterProvider, EventSubEvent, TwitchApiPayload {
    String id;
    UserInfo broadcaster;
    StreamType type;
    @Serdeable.Deserializable(using = ISOInstantSerde.class)
    @Serdeable.Serializable(using = ISOInstantSerde.class)
    Instant startedAt;

}
