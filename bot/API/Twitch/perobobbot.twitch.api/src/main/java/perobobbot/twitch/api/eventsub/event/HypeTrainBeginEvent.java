package perobobbot.twitch.api.eventsub.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.api.TwitchApiPayload;
import perobobbot.twitch.api.UserInfo;
import perobobbot.twitch.api.serde.ISOInstantSerde;
import perobobbot.twitch.api.serde.MySerdeable;

import java.time.Instant;

@Value
@Serdeable
public class HypeTrainBeginEvent implements HypeTrainEvent, TwitchApiPayload {

    @NonNull String id;
    @NonNull UserInfo broadcaster;
    int total;

    int progress;
    int goal;
    @JsonProperty("top_contributions")
    @NonNull ImmutableList<Contribution> topContributions;
    @JsonProperty("last_contribution")
    @NonNull Contribution lastContribution;

    @MySerdeable(property = "started_at",serwith = ISOInstantSerde.class, deserwith = ISOInstantSerde.class)
    @NonNull Instant startedAt;
    @MySerdeable(property = "expires_at",serwith = ISOInstantSerde.class, deserwith = ISOInstantSerde.class)
    @NonNull Instant expiresAt;

}
