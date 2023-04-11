package perobobbot.twitch.api.eventsub.event;

import io.micronaut.serde.annotation.Serdeable;
import io.micronaut.serde.config.naming.SnakeCaseStrategy;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
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
public class ChannelPollProgressEvent extends PollEvent {

    @Serdeable.Serializable(using = ISOInstantSerde.class)
    @Serdeable.Deserializable(using = ISOInstantSerde.class)
    @NonNull Instant endsAt;

    @java.beans.ConstructorProperties({"id", "broadcaster", "title", "choices", "bitsVoting", "channelPointsVoting", "startedAt","endsAt"})
    public ChannelPollProgressEvent(@NonNull String id,
                                    @NonNull UserInfo broadcaster,
                                    @NonNull String title,
                                    @NonNull List<PollChoices> choices,
                                    @NonNull Voting bitsVoting,
                                    @NonNull Voting channelPointsVoting,
                                    @NonNull @Serdeable.Deserializable(using = ISOInstantSerde.class) @Serdeable.Serializable(using = ISOInstantSerde.class) Instant startedAt,
                                    @NonNull @Serdeable.Deserializable(using = ISOInstantSerde.class) @Serdeable.Serializable(using = ISOInstantSerde.class)  Instant endsAt) {
        super(id, broadcaster, title, choices, bitsVoting, channelPointsVoting, startedAt);
        this.endsAt = endsAt;
    }

}
