package perobobbot.service.jpa;

import io.micronaut.data.model.Pageable;
import jakarta.inject.Singleton;
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

    private final UserIdentityRepository userIdentityRepository;
    private final JoinedChannelRepository joinedChannelRepository;
    private final UserTypeProvider userTypeProvider;

    @Override
    public UserIdentity getUserIdentity(long userIdentityId) {
        return userIdentityRepository.getById(userIdentityId).toView();
    }

    @Override
    public Optional<UserInfo> findUserInfo(Identity identity) {
        return userIdentityRepository.findByIdentification(identity)
                                     .map(UserIdentityEntity::toUserInfo);
    }

    @Override
    public UserIdentity saveUserInfo(UserInfo userInfo) {
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
    public Optional<UserIdentity> findUserIdentity(Id id) {
        return id.accept(new UserIdentityFinder(userIdentityRepository)).map(UserIdentityEntity::toView);
    }

    @Override
    public Set<JoinedChannel> listJoinedChannels(long userIdentityId) {
        return joinedChannelRepository.getByUserIdentityId(userIdentityId)
                                      .map(JoinedChannelEntity::toView)
                                      .collect(Collectors.toSet());
    }

    @Override
    public JoinedChannel getJoinedChannel(long userIdentityId, String channelName) {
        return joinedChannelRepository.getByUserIdentityIdAndName(userIdentityId, channelName).toView();
    }

    @Override
    public UserIdentity getBotForPlatform(Platform platform) {
        return userIdentityRepository.findByPlatformAndUserType(platform, UserType.BOT)
                                     .orElseThrow(() -> new NoBotDefined(platform)).toView();
    }

    @Override
    public Optional<UserIdentity> findBotForPlatform(Platform platform) {
        return userIdentityRepository.findByPlatformAndUserType(platform, UserType.BOT)
                                     .map(UserIdentityEntity::toView);
    }

    @Override
    public List<UserIdentity> listUserIdentities(int page, int size) {
        return userIdentityRepository.findAll(Pageable.from(page, size))
                                     .getContent()
                                     .stream()
                                     .map(UserIdentityEntity::toView)
                                     .toList();
    }

    @Override
    public List<UserIdentity> listUserIdentities(Platform platform, int page, int size) {
        return userIdentityRepository.findByPlatform(platform, Pageable.from(page, size))
                                     .getContent()
                                     .stream()
                                     .map(UserIdentityEntity::toView)
                                     .toList();
    }

    @Override
    public JoinedChannel joinChannel(long userIdentityId, String channelName, boolean readOnly) {
        final var userIdentity = userIdentityRepository.getById(userIdentityId);
        final var result = userIdentity.joinedChannel(channelName, readOnly);
        userIdentityRepository.save(userIdentity);
        return joinedChannelRepository.save(result).toView();
    }

    @Override
    public void partChannel(long userIdentityId, String channelName) {
        final var userIdentity = userIdentityRepository.getById(userIdentityId);
        if (userIdentity.partChannel(channelName)) {
            userIdentityRepository.save(userIdentity);
        }
    }

    @Override
    public Map<Identity, UserIdentity> findBots() {
        return userIdentityRepository.findByUserType(UserType.BOT)
                                     .map(UserIdentityEntity::toView)
                                     .collect(Collectors.toMap(UserIdentity::identity, Function.identity()));
    }
}
