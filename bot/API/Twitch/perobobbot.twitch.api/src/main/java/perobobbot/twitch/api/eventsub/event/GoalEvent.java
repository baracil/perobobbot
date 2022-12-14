package perobobbot.twitch.api.eventsub.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.api.GoalType;
import perobobbot.twitch.api.TwitchApiPayload;
import perobobbot.twitch.api.UserInfo;
import perobobbot.twitch.api.serde.ISOInstantSerde;
import perobobbot.twitch.api.serde.MySerdeable;

import java.time.Instant;
import java.util.Optional;

@Value
@Serdeable
public class GoalEvent implements TwitchApiPayload, BroadcasterProvider, EventSubEvent {
    @NonNull String id;
    @NonNull UserInfo broadcaster;
    @JsonProperty("type")
    @NonNull GoalType goalType;
    @NonNull String description;
    @JsonProperty(value = "is_achieved",defaultValue = "false")
    boolean achieved;
    @JsonProperty("current_amount")
    int currentAmount;
    @JsonProperty("target_amount")
    int targetAmount;
    @MySerdeable(property = "started_at",serwith = ISOInstantSerde.class, deserwith = ISOInstantSerde.class)
    @NonNull Instant startedAt;

    @MySerdeable(property = "ended_at",serwith = ISOInstantSerde.class, deserwith = ISOInstantSerde.class)
    @Nullable Instant endedAt;


    public @NonNull Optional<Instant> getEndedAt() {
        return Optional.ofNullable(endedAt);
    }
}
