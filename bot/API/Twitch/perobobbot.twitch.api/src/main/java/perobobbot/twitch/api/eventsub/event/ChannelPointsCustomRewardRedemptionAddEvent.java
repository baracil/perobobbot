package perobobbot.twitch.api.eventsub.event;

import io.micronaut.serde.annotation.Serdeable;
import io.micronaut.serde.config.naming.SnakeCaseStrategy;
import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.api.RewardRedemptionStatus;
import perobobbot.twitch.api.TwitchApiPayload;
import perobobbot.twitch.api.UserInfo;
import perobobbot.twitch.api.serde.ISOInstantSerde;

import java.time.Instant;

@Value
@Serdeable(naming = SnakeCaseStrategy.class)
public class ChannelPointsCustomRewardRedemptionAddEvent implements BroadcasterProvider, EventSubEvent, TwitchApiPayload {
    @NonNull String id;
    @NonNull UserInfo broadcaster;
    @NonNull UserInfo user;
    @NonNull String userInput;
    @NonNull RewardRedemptionStatus status;
    @NonNull Reward reward;

    @Serdeable.Serializable(using = ISOInstantSerde.class)
    @Serdeable.Deserializable(using = ISOInstantSerde.class)
    @NonNull Instant redeemedAt;

}
