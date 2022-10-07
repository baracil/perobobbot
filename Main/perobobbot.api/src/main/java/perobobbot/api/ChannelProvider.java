package perobobbot.api;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.api.data.Channel;

public interface ChannelProvider {

    @NonNull ImmutableSet<Channel> getChannelsForUser(@NonNull Identification identification);

    default @NonNull ChannelProviderForUser forUserIdentity(@NonNull Identification identification) {
        return () -> getChannelsForUser(identification);
    }

}
