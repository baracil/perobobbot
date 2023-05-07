package perobobbot.twitch.api.eventsub.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Value;
import perobobbot.twitch.api.TwitchApiPayload;
import perobobbot.twitch.api.UserInfo;
import perobobbot.twitch.api.serde.ISOInstantSerde;
import perobobbot.twitch.api.serde.MySerdeable;

import java.time.Instant;
import java.util.List;

@Value
@Serdeable
public class HypeTrainBeginEvent implements HypeTrainEvent, TwitchApiPayload {

    String id;
    UserInfo broadcaster;
    int total;

    int progress;
    int goal;
    @JsonProperty("top_contributions")
    List<Contribution> topContributions;
    @JsonProperty("last_contribution")
    Contribution lastContribution;

    @MySerdeable(property = "started_at",serwith = ISOInstantSerde.class, deserwith = ISOInstantSerde.class)
    Instant startedAt;
    @MySerdeable(property = "expires_at",serwith = ISOInstantSerde.class, deserwith = ISOInstantSerde.class)
    Instant expiresAt;

}
