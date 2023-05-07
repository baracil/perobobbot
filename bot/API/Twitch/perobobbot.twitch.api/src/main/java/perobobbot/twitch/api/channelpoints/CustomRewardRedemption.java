package perobobbot.twitch.api.channelpoints;

import lombok.Value;
import perobobbot.twitch.api.RewardRedemptionStatus;
import perobobbot.twitch.api.TwitchApiPayload;
import perobobbot.twitch.api.UserInfo;

import java.time.Instant;

@Value
public class CustomRewardRedemption implements TwitchApiPayload {

    String id;
    UserInfo broadcaster;
    UserInfo user;
    BasicReward reward;
    String userInput;
    RewardRedemptionStatus status;
    Instant redeemedAt;

}
