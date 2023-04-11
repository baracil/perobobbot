package perobobbot.twitch.api.eventsub.event;

import io.micronaut.serde.annotation.Serdeable;
import io.micronaut.serde.config.naming.SnakeCaseStrategy;
import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.api.TwitchApiPayload;
import perobobbot.twitch.api.UserInfo;
import perobobbot.twitch.api.serde.ISOInstantSerde;

import java.time.Instant;
import java.util.List;

@Value
@Serdeable(naming = SnakeCaseStrategy.class)
public class HypeTrainProgressEvent implements HypeTrainEvent, TwitchApiPayload {

    @NonNull String id;
    @NonNull UserInfo broadcaster;
    int total;

    int level;
    int progress;
    int goal;
    @NonNull List<Contribution> topContributions;
    @NonNull Contribution lastContribution;

    @Serdeable.Serializable(using = ISOInstantSerde.class)
    @Serdeable.Deserializable(using = ISOInstantSerde.class)
    @NonNull Instant startedAt;
    @Serdeable.Serializable(using = ISOInstantSerde.class)
    @Serdeable.Deserializable(using = ISOInstantSerde.class)
    @NonNull Instant expiresAt;

}
