package perobobbot.api.data;

import lombok.NonNull;

import java.util.List;

public class JoinedChannelNotFound extends EntityNotFound {

    private final long userIdentityId;
    private final String channelName;

    public JoinedChannelNotFound(long userIdentityId, @NonNull String channelName) {
        super("Could not found a JoinedChannel for userIdentityId='"+userIdentityId+"' and channelName='"+channelName+"'");
        this.userIdentityId = userIdentityId;
        this.channelName = channelName;
    }

    @Override
    public @NonNull List<Criteria> searchedCriteria() {
        return List.of(new Criteria("userIdentityId",userIdentityId),new Criteria("channelName",channelName));
    }
}
