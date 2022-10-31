package perobobbot.service.jpa;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.api.BotId;
import perobobbot.api.Id;
import perobobbot.api.Identity;
import perobobbot.api.IdentityByLogin;
import perobobbot.api.data.UserType;
import perobobbot.domain.jpa.entity.UserIdentityEntity;
import perobobbot.domain.jpa.repository.UserIdentityRepository;

import java.util.Optional;

@RequiredArgsConstructor
public class UserIdentityFinder implements Id.Visitor<Optional<UserIdentityEntity>> {

    private final @NonNull UserIdentityRepository userIdentityRepository;

    @Override
    public @NonNull Optional<UserIdentityEntity> visit(@NonNull Identity identity) {
        return userIdentityRepository.findByPlatformAndUserId(identity.platform(),identity.userId());
    }

    @Override
    public @NonNull Optional<UserIdentityEntity> visit(@NonNull BotId botId) {
        return userIdentityRepository.findByPlatformAndUserType(botId.platform(), UserType.BOT);
    }

    @Override
    public @NonNull Optional<UserIdentityEntity> visit(@NonNull IdentityByLogin identityByLogin) {
        return userIdentityRepository.findByPlatformAndLoginIgnoreCase(identityByLogin.platform(),identityByLogin.login());
    }
}
