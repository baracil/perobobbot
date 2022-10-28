package perobobbot.twitch.web.api.eventsub.event;

import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.web.api.RewardRedemptionStatus;
import perobobbot.twitch.web.api.UserInfo;

import java.time.Instant;

@Value
public class ChannelPointsCustomRewardRedemptionAddEvent implements BroadcasterProvider, EventSubEvent {
    @NonNull String id;
    @NonNull UserInfo broadcaster;
    @NonNull UserInfo user;
    @NonNull String userInput;
    @NonNull RewardRedemptionStatus status;
    @NonNull Reward reward;
    @NonNull Instant redeemedAt;

}
