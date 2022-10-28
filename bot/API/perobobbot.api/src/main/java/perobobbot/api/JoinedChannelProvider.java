package perobobbot.api;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.api.data.JoinedChannel;

public interface JoinedChannelProvider {

    @NonNull ImmutableSet<JoinedChannel> listJoinedChannels(long userIdentityId);

    default @NonNull JoinedChannelProviderForUser forUserIdentity(long userIdentityId) {
        return () -> listJoinedChannels(userIdentityId);
    }

}
