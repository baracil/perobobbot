package perobobbot.service.jpa.service;

import fpc.tools.cipher.TextCipher;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.api.data.Platform;
import perobobbot.api.data.view.UserToken;
import perobobbot.service.api.UserTokenService;
import perobobbot.service.jpa.domain.UserIdentityEntity;
import perobobbot.service.jpa.domain.UserTokenEntity;
import perobobbot.service.jpa.repository.UserIdentityRepository;
import perobobbot.service.jpa.repository.UserTokenRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Singleton
@RequiredArgsConstructor
@Transactional
public class JpaUserTokenService implements UserTokenService {

    private final @NonNull UserIdentityRepository userIdentityRepository;
    private final @NonNull UserTokenRepository userTokenRepository;
    private final @NonNull
    @Named("Db") TextCipher textCipher;

    @Override
    public @NonNull Optional<UserToken.Decrypted> findUserToken(@NonNull Platform platform) {
        return userTokenRepository.findByPlatform(platform).map(UserTokenEntity::toView).map(textCipher::decrypt);
    }

    @Override
    public @NonNull Optional<UserToken.Decrypted> findUserToken(@NonNull Platform platform, @NonNull String login) {
        return userTokenRepository.findByPlatformAndUserIdentityLogin(platform,login).map(UserTokenEntity::toView).map(textCipher::decrypt);
    }

    @Override
    public @NonNull Optional<UserToken.Decrypted> findMainUserToken(@NonNull Platform platform) {
        return userTokenRepository.findByPlatformAndMainTrue(platform).map(UserTokenEntity::toView).map(textCipher::decrypt);
    }

    @Override
    public void deleteUserToken(@NonNull Platform platform) {
        userTokenRepository.deleteByPlatform(platform);
    }

    @Override
    public void setUserToken(@NonNull UserToken.Decrypted userToken) {
        final var identity = userIdentityRepository.findByPlatformAndUserId(userToken.identity()).orElse(null);
        final var encrypted = userToken.encrypt(textCipher);

        if (identity == null) {
            newUserIdentityWithToken(encrypted);
        } else {
            updateUserIdentityWithToken(identity, encrypted);
        }

    }

    private void updateUserIdentityWithToken(@NonNull UserIdentityEntity userIdentity, @NonNull UserToken.Encrypted userToken) {
        userIdentity.updateToken(userToken);
        userIdentityRepository.update(userIdentity);
    }

    private void newUserIdentityWithToken(@NonNull UserToken.Encrypted userToken) {
        final var userIdentity = UserIdentityEntity.createWith(userToken.identity());
        userIdentity.updateToken(userToken);
        userIdentityRepository.save(userIdentity);
    }
}
