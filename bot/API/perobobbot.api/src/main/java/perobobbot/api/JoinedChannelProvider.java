package perobobbot.api;

import perobobbot.api.data.JoinedChannel;

import java.util.Set;

public interface JoinedChannelProvider {

    Set<JoinedChannel> listJoinedChannels(long userIdentityId);

    default JoinedChannelProviderForUser forUserIdentity(long userIdentityId) {
        return () -> listJoinedChannels(userIdentityId);
    }

}
