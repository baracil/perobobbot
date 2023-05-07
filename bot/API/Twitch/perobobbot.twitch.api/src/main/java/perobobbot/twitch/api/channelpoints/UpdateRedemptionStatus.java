package perobobbot.twitch.api.channelpoints;

import lombok.Value;
import perobobbot.twitch.api.RewardRedemptionStatus;

@Value
public class UpdateRedemptionStatus {

    RewardRedemptionStatus status;
}
