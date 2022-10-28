package perobobbot.twitch.web.api.channelpoints;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.web.api.RewardRedemptionStatus;
import perobobbot.twitch.web.api.serde.RewardRedemptionStatusSerializer;

@Value
public class UpdateRedemptionStatus {

    @JsonSerialize(using = RewardRedemptionStatusSerializer.class)
    @NonNull RewardRedemptionStatus status;
}
