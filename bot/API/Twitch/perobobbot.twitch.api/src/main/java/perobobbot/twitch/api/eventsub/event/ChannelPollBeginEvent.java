package perobobbot.twitch.api.eventsub.event;

import io.micronaut.serde.annotation.Serdeable;
import io.micronaut.serde.config.naming.SnakeCaseStrategy;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import perobobbot.twitch.api.UserInfo;
import perobobbot.twitch.api.serde.ISOInstantSerde;

import java.time.Instant;
import java.util.List;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Serdeable(naming = SnakeCaseStrategy.class)
public class ChannelPollBeginEvent extends PollEvent {

    @Serdeable.Serializable(using = ISOInstantSerde.class)
    @Serdeable.Deserializable(using = ISOInstantSerde.class)
    Instant endsAt;

    @java.beans.ConstructorProperties({"id", "broadcaster", "title", "choices", "bitsVoting", "channelPointsVoting", "startedAt","endsAt"})
    public ChannelPollBeginEvent(String id,
                                 UserInfo broadcaster,
                                 String title,
                                 List<PollChoices> choices,
                                 Voting bitsVoting,
                                 Voting channelPointsVoting,
                                 @Serdeable.Deserializable(using = ISOInstantSerde.class) Instant startedAt,
                                 @Serdeable.Deserializable(using = ISOInstantSerde.class) Instant endsAt) {
        super(id, broadcaster, title, choices, bitsVoting, channelPointsVoting, startedAt);
        this.endsAt = endsAt;
    }
}
