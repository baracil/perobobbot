package perobobbot.service.api;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.api.data.Channel;
import perobobbot.api.data.Platform;

public interface ChannelService  {

    @NonNull ImmutableSet<Channel> getChannels(int page, int size);

    @NonNull Channel createChannel(@NonNull Platform platform, @NonNull String name);

    void deleteChannel(@NonNull Platform platform, @NonNull String name);


    @NonNull Channel getChannelById(long id);

    @NonNull Channel getChannel(@NonNull Platform platform, @NonNull String name);
}
