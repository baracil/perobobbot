package perobobbot.twitch.service.client;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import jakarta.inject.Singleton;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.twitch.api.channel.ChannelInformation;
import perobobbot.twitch.service.api.ChannelService;
import perobobbot.twitch.web.client.ChannelClient;

@Singleton
@RequiredArgsConstructor
public class ClientChannelService implements ChannelService {

    private final @NonNull ChannelClient channelClient;

    @Override
    public @NonNull ImmutableList<ChannelInformation> getChannelInformation(@NonNull ImmutableSet<String> broadcasterIds) {
        final var channelInformation = channelClient.getChannelInformation(broadcasterIds);
        if (channelInformation == null) {
            return ImmutableList.of();
        }
        return ImmutableList.copyOf(channelInformation.getData());
    }

}
