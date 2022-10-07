package perobobbot.service.api;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.api.ChannelProvider;
import perobobbot.api.Identification;
import perobobbot.api.data.Channel;

public interface ChannelService extends ChannelProvider {

    @NonNull ImmutableSet<Channel> getChannelsForUser(@NonNull Identification identification);

    @NonNull Channel joinChannel(@NonNull Identification identification,
                                 @NonNull String channelName,
                                 boolean mute);

    void partChannel(@NonNull Identification identification, @NonNull String channelName);



}
