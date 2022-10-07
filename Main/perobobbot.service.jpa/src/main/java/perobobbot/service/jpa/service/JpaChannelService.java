package perobobbot.service.jpa.service;

import com.google.common.collect.ImmutableSet;
import jakarta.inject.Singleton;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.api.Identification;
import perobobbot.api.data.Channel;
import perobobbot.service.api.ChannelService;
import perobobbot.service.jpa.domain.ChannelEntity;
import perobobbot.service.jpa.repository.UserIdentityRepository;

import javax.transaction.Transactional;

@Singleton
@RequiredArgsConstructor
@Transactional
public class JpaChannelService implements ChannelService {

    private final @NonNull UserIdentityRepository userIdentityRepository;

    @Override
    public @NonNull ImmutableSet<Channel> getChannelsForUser(@NonNull Identification identification) {
        return userIdentityRepository.getByIdentification(identification)
                                     .getChannels()
                                     .stream()
                                     .map(ChannelEntity::toView)
                                     .collect(ImmutableSet.toImmutableSet());
    }

    @Override
    public @NonNull Channel joinChannel(@NonNull Identification identification, @NonNull String channelName, boolean mute) {
        final var identity = userIdentityRepository.getByIdentification(identification);
        final var channel = identity.addChannel(channelName,mute);
        userIdentityRepository.save(identity);
        return channel.toView();
    }

    @Override
    public void partChannel(@NonNull Identification identification, @NonNull String channelName) {
        final var identity = userIdentityRepository.getByIdentification(identification);
        identity.removeChannel(channelName);
        userIdentityRepository.save(identity);
    }
}
