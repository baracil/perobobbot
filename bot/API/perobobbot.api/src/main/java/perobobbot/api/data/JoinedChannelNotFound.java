package perobobbot.api.data;

import java.util.List;

public class JoinedChannelNotFound extends EntityNotFound {

    private final long userIdentityId;
    private final String channelName;

    public JoinedChannelNotFound(long userIdentityId, String channelName) {
        super("Could not found a JoinedChannel for userIdentityId='"+userIdentityId+"' and channelName='"+channelName+"'");
        this.userIdentityId = userIdentityId;
        this.channelName = channelName;
    }

    @Override
    public List<Criteria> searchedCriteria() {
        return List.of(new Criteria("userIdentityId",userIdentityId),new Criteria("channelName",channelName));
    }
}
