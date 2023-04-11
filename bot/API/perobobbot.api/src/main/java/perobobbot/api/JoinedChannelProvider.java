package perobobbot.api;

import lombok.NonNull;
import perobobbot.api.data.JoinedChannel;

import java.util.Set;

public interface JoinedChannelProvider {

    @NonNull Set<JoinedChannel> listJoinedChannels(long userIdentityId);

    default @NonNull JoinedChannelProviderForUser forUserIdentity(long userIdentityId) {
        return () -> listJoinedChannels(userIdentityId);
    }

}
