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
public class HypeTrainEndEvent implements HypeTrainEvent, BroadcasterProvider, EventSubEvent {

    String id;
    UserInfo broadcaster;
    int level;
    int total;
    @JsonProperty("top_contributions")
    List<Contribution> topContributions;
    @MySerdeable(property = "started_at",serwith = ISOInstantSerde.class, deserwith = ISOInstantSerde.class)
    Instant startedAt;
    @MySerdeable(property = "ended_at",serwith = ISOInstantSerde.class, deserwith = ISOInstantSerde.class)
    Instant endedAt;
    @MySerdeable(property = "cooldown_ends_at",serwith = ISOInstantSerde.class, deserwith = ISOInstantSerde.class)
    Instant cooldownEndsAt;

}
