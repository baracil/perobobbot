package perobobbot.twitch.api.eventsub.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.api.UserInfo;
import perobobbot.twitch.api.serde.ISOInstantSerde;
import perobobbot.twitch.api.serde.MySerdeable;

import java.time.Instant;

@Value
@Serdeable
public class ChannelPredictionEndEvent implements PredicationEvent {
    @NonNull String id;
    @NonNull UserInfo broadcaster;
    @NonNull String title;
    @JsonProperty("winning_outcome_id")
    @NonNull String winningOutcomeId;
    @NonNull ImmutableList<Outcome> outcomes;
    @NonNull PredicationStatus status;

    @MySerdeable(property = "started_at",serwith = ISOInstantSerde.class, deserwith = ISOInstantSerde.class)
    @NonNull Instant startedAt;
    @MySerdeable(property = "ended_at",serwith = ISOInstantSerde.class, deserwith = ISOInstantSerde.class)
    @NonNull Instant endedAt;

}
