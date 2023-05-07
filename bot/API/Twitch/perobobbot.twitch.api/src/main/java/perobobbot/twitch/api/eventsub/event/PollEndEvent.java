package perobobbot.twitch.api.eventsub.event;

import io.micronaut.serde.annotation.Serdeable;
import io.micronaut.serde.config.naming.SnakeCaseStrategy;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import perobobbot.twitch.api.UserInfo;
import perobobbot.twitch.api.eventsub.PollStatus;
import perobobbot.twitch.api.serde.ISOInstantSerde;

import java.time.Instant;
import java.util.List;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Serdeable(naming = SnakeCaseStrategy.class)
public class PollEndEvent extends PollEvent {

    PollStatus status;
    Instant endedAt;

    @java.beans.ConstructorProperties({"id", "broadcaster", "title", "choices", "bitsVoting", "channelPointsVoting", "startedAt","status","endedAt"})
    public PollEndEvent(String id,
                        UserInfo broadcaster,
                        String title,
                        List<PollChoices> choices,
                        Voting bitsVoting,
                        Voting channelPointsVoting,
                        @Serdeable.Serializable(using = ISOInstantSerde.class) @Serdeable.Deserializable(using = ISOInstantSerde.class) Instant startedAt,
                        PollStatus status,
                        @Serdeable.Serializable(using = ISOInstantSerde.class) @Serdeable.Deserializable(using = ISOInstantSerde.class)  Instant endedAt) {
        super(id, broadcaster, title, choices, bitsVoting, channelPointsVoting, startedAt);
        this.endedAt = endedAt;
        this.status = status;
    }

}
