package perobobbot.twitch.api.eventsub.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import io.micronaut.serde.config.naming.SnakeCaseStrategy;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.api.TwitchApiPayload;
import perobobbot.twitch.api.UserInfo;
import perobobbot.twitch.api.serde.ISOInstantSerde;

import java.time.Instant;
import java.util.Optional;

@Value
@Serdeable(naming = SnakeCaseStrategy.class)
public class ChannelBanEvent implements BroadcasterProvider, EventSubEvent, TwitchApiPayload {
    @NonNull UserInfo user;
    @NonNull UserInfo broadcaster;
    @NonNull UserInfo moderator;
    @NonNull String reason;
    @Getter(AccessLevel.NONE)
    @Serdeable.Serializable(using = ISOInstantSerde.class)
    @Serdeable.Deserializable(using = ISOInstantSerde.class)
    @Nullable Instant endsAt;
    @JsonProperty("is_permanent")
    boolean permanent;

    public @NonNull Optional<Instant> getEndsAt() {
        return Optional.ofNullable(endsAt);
    }
}
