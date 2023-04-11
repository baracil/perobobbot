package perobobbot.twitch.service.api;

import lombok.NonNull;
import perobobbot.twitch.api.ChannelInformation;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ChannelService {

    @UserAuth @AppAuth
    @NonNull List<ChannelInformation> getChannelInformation(@NonNull Set<String> broadcasterIds);

    default @NonNull Optional<ChannelInformation> getChannelInformation(@NonNull String broadcasterId) {
        final var channels = getChannelInformation(Set.of(broadcasterId));
        return channels.isEmpty()?Optional.empty():Optional.of(channels.get(0));
    }
}
