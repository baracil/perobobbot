package perobobbot.api;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.api.data.Channel;

/**
 * Provides the channels a user (see {@link perobobbot.api.data.view.UserIdentity})
 * joined
 */
public interface ChannelProviderForUser {

    @NonNull ImmutableSet<Channel> getChannels();
}
