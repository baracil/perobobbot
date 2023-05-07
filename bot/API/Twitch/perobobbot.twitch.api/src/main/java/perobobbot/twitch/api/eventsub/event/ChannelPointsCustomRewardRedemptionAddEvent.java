package perobobbot.twitch.api.eventsub.event;

import io.micronaut.serde.annotation.Serdeable;
import io.micronaut.serde.config.naming.SnakeCaseStrategy;
import lombok.Value;
import perobobbot.twitch.api.RewardRedemptionStatus;
import perobobbot.twitch.api.TwitchApiPayload;
import perobobbot.twitch.api.UserInfo;
import perobobbot.twitch.api.serde.ISOInstantSerde;

import java.time.Instant;

@Value
@Serdeable(naming = SnakeCaseStrategy.class)
public class ChannelPointsCustomRewardRedemptionAddEvent implements BroadcasterProvider, EventSubEvent, TwitchApiPayload {
    String id;
    UserInfo broadcaster;
    UserInfo user;
    String userInput;
    RewardRedemptionStatus status;
    Reward reward;

    @Serdeable.Serializable(using = ISOInstantSerde.class)
    @Serdeable.Deserializable(using = ISOInstantSerde.class)
    Instant redeemedAt;

}
