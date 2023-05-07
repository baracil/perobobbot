package perobobbot.twitch.service.api;

import perobobbot.twitch.api.ChannelInformation;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ChannelService {

    @UserAuth @AppAuth
    List<ChannelInformation> getChannelInformation(Set<String> broadcasterIds);

    default Optional<ChannelInformation> getChannelInformation(String broadcasterId) {
        final var channels = getChannelInformation(Set.of(broadcasterId));
        return channels.isEmpty()?Optional.empty():Optional.of(channels.get(0));
    }
}
