package perobobbot.twitch.service.api;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.twitch.api.Channel;

import java.util.Optional;

public interface ChannelService {

    @UserAuth @AppAuth
    @NonNull ImmutableList<Channel> getChannelInformation(@NonNull ImmutableSet<String> broadcasterIds);

    default @NonNull Optional<Channel> getChannelInformation(@NonNull String broadcasterId) {
        final var channels = getChannelInformation(ImmutableSet.of(broadcasterId));
        return channels.isEmpty()?Optional.empty():Optional.of(channels.get(0));
    }
}
