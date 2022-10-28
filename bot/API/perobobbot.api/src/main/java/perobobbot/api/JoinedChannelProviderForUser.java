package perobobbot.api;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.api.data.JoinedChannel;
import perobobbot.api.data.UserIdentity;

/**
 * Provides the channels a user (see {@link UserIdentity})
 * joined
 */
public interface JoinedChannelProviderForUser {

    @NonNull ImmutableSet<JoinedChannel> getChannels();
}
