package perobobbot.twitch.service.client;

import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import perobobbot.twitch.api.ChannelInformation;
import perobobbot.twitch.service.api.ChannelService;
import perobobbot.twitch.web.client.ChannelClient;

import java.util.List;
import java.util.Set;

@Singleton
@RequiredArgsConstructor
public class ClientChannelService implements ChannelService {

    private final ChannelClient channelClient;

    @Override
    public List<ChannelInformation> getChannelInformation(Set<String> broadcasterIds) {
        final var channelInformation = channelClient.getChannelInformation(broadcasterIds);
        if (channelInformation == null) {
            return List.of();
        }
        return List.of(channelInformation.getData());
    }

}
