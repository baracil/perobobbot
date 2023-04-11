package perobobbot.api;

import lombok.NonNull;
import perobobbot.api.data.JoinedChannel;
import perobobbot.api.data.UserIdentity;

import java.util.Set;

/**
 * Provides the channels a user (see {@link UserIdentity})
 * joined
 */
public interface JoinedChannelProviderForUser {

    @NonNull Set<JoinedChannel> getChannels();
}
