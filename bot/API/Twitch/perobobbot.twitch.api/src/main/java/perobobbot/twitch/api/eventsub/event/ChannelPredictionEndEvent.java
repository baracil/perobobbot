package perobobbot.twitch.api.eventsub.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Value;
import perobobbot.twitch.api.UserInfo;
import perobobbot.twitch.api.serde.ISOInstantSerde;
import perobobbot.twitch.api.serde.MySerdeable;

import java.time.Instant;
import java.util.List;

@Value
@Serdeable
public class ChannelPredictionEndEvent implements PredicationEvent {
    String id;
    UserInfo broadcaster;
    String title;
    @JsonProperty("winning_outcome_id")
    String winningOutcomeId;
    List<Outcome> outcomes;
    PredicationStatus status;

    @MySerdeable(property = "started_at",serwith = ISOInstantSerde.class, deserwith = ISOInstantSerde.class)
    Instant startedAt;
    @MySerdeable(property = "ended_at",serwith = ISOInstantSerde.class, deserwith = ISOInstantSerde.class)
    Instant endedAt;

}
