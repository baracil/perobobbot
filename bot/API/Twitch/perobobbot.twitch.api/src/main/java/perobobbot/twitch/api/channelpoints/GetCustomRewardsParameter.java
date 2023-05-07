package perobobbot.twitch.api.channelpoints;

import lombok.Value;

@Value
public class GetCustomRewardsParameter {

    String[] ids;
    boolean onlyManageableRewards;

}
