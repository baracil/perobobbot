package perobobbot.service.jpa.service;

import com.google.common.collect.ImmutableSet;
import jakarta.inject.Singleton;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.api.data.Channel;
import perobobbot.api.data.Platform;
import perobobbot.service.api.ChannelService;
import perobobbot.service.jpa.domain.ChannelEntity;
import perobobbot.service.jpa.repository.UserIdentityRepository;

@Singleton
@RequiredArgsConstructor
public class JpaChannelService implements ChannelService {

    private final @NonNull UserIdentityRepository userIdentityRepository;

    @Override
    public @NonNull ImmutableSet<Channel> getChannelsForUser(@NonNull Platform platform, @NonNull String userId) {
        return userIdentityRepository.getByPlatformAndUserId(platform, userId)
                                     .getChannels()
                                     .stream()
                                     .map(ChannelEntity::toView)
                                     .collect(ImmutableSet.toImmutableSet());
    }
}
