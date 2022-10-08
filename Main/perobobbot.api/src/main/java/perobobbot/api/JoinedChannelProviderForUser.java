package perobobbot.api;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.api.data.JoinedChannel;

/**
 * Provides the channels a user (see {@link perobobbot.api.data.view.UserIdentity})
 * joined
 */
public interface JoinedChannelProviderForUser {

    @NonNull ImmutableSet<JoinedChannel> getChannels();
}
