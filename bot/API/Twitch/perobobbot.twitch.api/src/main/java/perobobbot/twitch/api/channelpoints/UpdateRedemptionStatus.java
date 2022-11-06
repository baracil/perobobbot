package perobobbot.twitch.api.channelpoints;

import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.api.RewardRedemptionStatus;

@Value
public class UpdateRedemptionStatus {

    @NonNull RewardRedemptionStatus status;
}
