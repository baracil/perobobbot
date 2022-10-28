package perobobbot.twitch.web.api.channelpoints;

import lombok.NonNull;
import lombok.Value;

@Value
public class GetCustomRewardsParameter {

    @NonNull String[] ids;
    boolean onlyManageableRewards;

}
