package perobobbot.twitch.web.api.channelpoints;

import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.api.TwitchApiPayload;
import perobobbot.twitch.web.api.RewardRedemptionStatus;
import perobobbot.twitch.web.api.UserInfo;

import java.time.Instant;

@Value
public class CustomRewardRedemption implements TwitchApiPayload {

    @NonNull String id;
    @NonNull UserInfo broadcaster;
    @NonNull UserInfo user;
    @NonNull BasicReward reward;
    @NonNull String userInput;
    @NonNull RewardRedemptionStatus status;
    @NonNull Instant redeemedAt;

}
