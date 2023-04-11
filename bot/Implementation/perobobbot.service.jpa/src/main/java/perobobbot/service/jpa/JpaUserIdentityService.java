package perobobbot.service.jpa;

import io.micronaut.data.model.Pageable;
import jakarta.inject.Singleton;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.api.Id;
import perobobbot.api.Identity;
import perobobbot.api.UserInfo;
import perobobbot.api.data.*;
import perobobbot.api.plugin.PerobobbotService;
import perobobbot.domain.jpa.entity.JoinedChannelEntity;
import perobobbot.domain.jpa.entity.UserIdentityEntity;
import perobobbot.domain.jpa.repository.JoinedChannelRepository;
import perobobbot.domain.jpa.repository.UserIdentityRepository;
import perobobbot.service.api.NoBotDefined;
import perobobbot.service.api.UserIdentityService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Singleton
@RequiredArgsConstructor
@Transactional
@PerobobbotService(serviceType = UserIdentityService.class, apiVersion = UserIdentityService.VERSION)
public class JpaUserIdentityService implements UserIdentityService {

    private final @NonNull UserIdentityRepository userIdentityRepository;
    private final @NonNull JoinedChannelRepository joinedChannelRepository;
    private final @NonNull UserTypeProvider userTypeProvider;

    @Override
    public @NonNull UserIdentity getUserIdentity(long userIdentityId) {
        return userIdentityRepository.getById(userIdentityId).toView();
    }

    @Override
    public @NonNull Optional<UserInfo> findUserInfo(@NonNull Identity identity) {
        return userIdentityRepository.findByIdentification(identity)
                                     .map(UserIdentityEntity::toUserInfo);
    }

    @Override
    public @NonNull UserIdentity saveUserInfo(@NonNull UserInfo userInfo) {
        final var identity = new Identity(userInfo.platform(), userInfo.userId());
        final var existing = userIdentityRepository.findByIdentification(identity).orElse(null);
        if (existing != null) {
            existing.update(userInfo.login(), userInfo.name());
            return userIdentityRepository.update(existing).toView();
        } else {
            return userIdentityRepository.save(UserIdentityEntity.createWith(userInfo, userTypeProvider.getUserType(userInfo))).toView();
        }
    }

    @Override
    public @NonNull Optional<UserIdentity> findUserIdentity(@NonNull Id id) {
        return id.accept(new UserIdentityFinder(userIdentityRepository)).map(UserIdentityEntity::toView);
    }

    @Override
    public @NonNull Set<JoinedChannel> listJoinedChannels(long userIdentityId) {
        return joinedChannelRepository.getByUserIdentityId(userIdentityId)
                                      .map(JoinedChannelEntity::toView)
                                      .collect(Collectors.toSet());
    }

    @Override
    public @NonNull JoinedChannel getJoinedChannel(long userIdentityId, @NonNull String channelName) {
        return joinedChannelRepository.getByUserIdentityIdAndName(userIdentityId, channelName).toView();
    }

    @Override
    public @NonNull UserIdentity getBotForPlatform(@NonNull Platform platform) {
        return userIdentityRepository.findByPlatformAndUserType(platform, UserType.BOT)
                                     .orElseThrow(() -> new NoBotDefined(platform)).toView();
    }

    @Override
    public @NonNull Optional<UserIdentity> findBotForPlatform(@NonNull Platform platform) {
        return userIdentityRepository.findByPlatformAndUserType(platform, UserType.BOT)
                                     .map(UserIdentityEntity::toView);
    }

    @Override
    public @NonNull List<UserIdentity> listUserIdentities(int page, int size) {
        return userIdentityRepository.findAll(Pageable.from(page, size))
                                     .getContent()
                                     .stream()
                                     .map(UserIdentityEntity::toView)
                                     .toList();
    }

    @Override
    public @NonNull List<UserIdentity> listUserIdentities(@NonNull Platform platform, int page, int size) {
        return userIdentityRepository.findByPlatform(platform, Pageable.from(page, size))
                                     .getContent()
                                     .stream()
                                     .map(UserIdentityEntity::toView)
                                     .toList();
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
    public @NonNull Map<Identity, UserIdentity> findBots() {
        return userIdentityRepository.findByUserType(UserType.BOT)
                                     .map(UserIdentityEntity::toView)
                                     .collect(Collectors.toMap(UserIdentity::identity, Function.identity()));
    }
}
