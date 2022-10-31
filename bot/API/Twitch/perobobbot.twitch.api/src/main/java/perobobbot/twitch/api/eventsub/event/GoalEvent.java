package perobobbot.twitch.api.eventsub.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.api.GoalType;
import perobobbot.twitch.api.TwitchApiPayload;
import perobobbot.twitch.api.UserInfo;

import java.time.Instant;
import java.util.Optional;

@Value
public class GoalEvent implements TwitchApiPayload, BroadcasterProvider, EventSubEvent {
    @NonNull String id;
    @NonNull UserInfo broadcaster;
    @NonNull GoalType type;
    @NonNull String description;
    @JsonProperty("is_achieved")
    boolean achieved;
    @JsonProperty("current_amount")
    int currentAmount;
    @JsonProperty("target_amount")
    int targetAmount;
    @JsonProperty("started_at")
    @NonNull Instant startedAt;
    @JsonProperty("ended_at")
    Instant endedAt;


    public @NonNull Optional<Instant> getEndedAt() {
        return Optional.ofNullable(endedAt);
    }
}
