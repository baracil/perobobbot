package perobobbot.service.jpa.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import io.micronaut.data.model.Pageable;
import jakarta.inject.Singleton;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.api.Identification;
import perobobbot.api.data.JoinedChannel;
import perobobbot.api.data.Platform;
import perobobbot.api.data.UserIdentityType;
import perobobbot.api.data.view.UserIdentity;
import perobobbot.service.api.NoBotDefined;
import perobobbot.service.api.UserIdentityService;
import perobobbot.service.jpa.domain.JoinedChannelEntity;
import perobobbot.service.jpa.domain.UserIdentityEntity;
import perobobbot.service.jpa.repository.JoinedChannelRepository;
import perobobbot.service.jpa.repository.UserIdentityRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Singleton
@RequiredArgsConstructor
@Transactional
public class JpaUserIdentityService implements UserIdentityService {

    private final @NonNull UserIdentityRepository userIdentityRepository;
    private final @NonNull JoinedChannelRepository joinedChannelRepository;

    @Override
    public @NonNull UserIdentity getUserIdentity(long userIdentityId) {
        return userIdentityRepository.getById(userIdentityId).toView();
    }

    @Override
    public @NonNull ImmutableSet<JoinedChannel> listJoinedChannels(long userIdentityId) {
        return joinedChannelRepository.getByUserIdentityId(userIdentityId)
                                      .map(JoinedChannelEntity::toView)
                                      .distinct()
                                      .collect(ImmutableSet.toImmutableSet());
    }

    @Override
    public @NonNull JoinedChannel getJoinedChannel(long userIdentityId, @NonNull String channelName) {
        return joinedChannelRepository.getByUserIdentityIdAndName(userIdentityId, channelName).toView();
    }

    @Override
    public @NonNull UserIdentity getBotForPlatform(@NonNull Platform platform) {
        return userIdentityRepository.findByPlatformAndUserIdentityType(platform, UserIdentityType.BOT)
                .orElseThrow(() -> new NoBotDefined(platform)).toView();
    }

    @Override
    public @NonNull Optional<UserIdentity> findBotForPlatform(@NonNull Platform platform) {
        return userIdentityRepository.findByPlatformAndUserIdentityType(platform,UserIdentityType.BOT)
                                     .map(UserIdentityEntity::toView);
    }

    @Override
    public @NonNull ImmutableList<UserIdentity> listUserIdentities(int page, int size) {
        return userIdentityRepository.findAll(Pageable.from(page, size))
                                     .getContent()
                                     .stream()
                                     .map(UserIdentityEntity::toView)
                                     .collect(ImmutableList.toImmutableList());
    }

    @Override
    public @NonNull ImmutableList<UserIdentity> listUserIdentities(@NonNull Platform platform, int page, int size) {
        return userIdentityRepository.findByPlatform(platform, Pageable.from(page, size))
                                     .getContent()
                                     .stream()
                                     .map(UserIdentityEntity::toView)
                                     .collect(ImmutableList.toImmutableList());
    }

    @Override
    public @NonNull JoinedChannel joinChannel(long userIdentityId, @NonNull String channelName, boolean readOnly) {
        final var userIdentity = userIdentityRepository.getById(userIdentityId);
        final var result = userIdentity.joinedChannel(channelName, readOnly);
        userIdentityRepository.save(userIdentity);
        return joinedChannelRepository.save(result).toView();
    }

    @Override
    public void partChannel(long userIdentityId, @NonNull String channelName) {
        final var userIdentity = userIdentityRepository.getById(userIdentityId);
        if (userIdentity.partChannel(channelName)) {
            userIdentityRepository.save(userIdentity);
        }
    }

    @Override
    public @NonNull ImmutableMap<Identification,UserIdentity> findBots() {
        return userIdentityRepository.findByUserIdentityType(UserIdentityType.BOT)
                                     .map(UserIdentityEntity::toView)
                                     .collect(ImmutableMap.toImmutableMap(u -> u.identification(),u -> u));
    }
}