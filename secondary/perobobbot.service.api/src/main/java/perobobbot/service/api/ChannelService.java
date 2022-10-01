package perobobbot.service.api;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.api.data.Channel;
import perobobbot.api.data.Platform;

public interface ChannelService {

    @NonNull ImmutableSet<Channel> getChannelsForUser(@NonNull Platform platform,
                                                      @NonNull String userId);

}
