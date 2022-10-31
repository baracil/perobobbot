package perobobbot.service.jpa;

import fpc.tools.cipher.TextCipher;
import fpc.tools.lang.Secret;
import fpc.tools.micronaut.EagerInit;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.api.UserInfo;
import perobobbot.api.data.*;
import perobobbot.domain.jpa.entity.UserIdentityEntity;
import perobobbot.domain.jpa.entity.UserTokenEntity;
import perobobbot.domain.jpa.repository.UserIdentityRepository;
import perobobbot.domain.jpa.repository.UserTokenRepository;
import perobobbot.service.api.UserTokenService;

import javax.transaction.Transactional;
import java.util.Optional;

@Singleton
@RequiredArgsConstructor
@Transactional
@EagerInit
public class JpaUserTokenService implements UserTokenService {

    private final @NonNull UserTypeProvider userTypeProvider;
    private final @NonNull UserIdentityRepository userIdentityRepository;
    private final @NonNull UserTokenRepository userTokenRepository;
    private final @NonNull
    @Named("Db") TextCipher textCipher;

    @Override
    public @NonNull Optional<UserToken.Decrypted> findUserToken(@NonNull Platform platform, @NonNull String userId) {
        return userTokenRepository.findByPlatformAndPlatformUserId(platform, userId).map(UserTokenEntity::toView).map(textCipher::decrypt);
    }

    @Override
    public @NonNull Optional<RefreshTokenInfo<Secret>> findUserRefreshToken(@NonNull Platform platform, @NonNull String userId) {
        return userTokenRepository.findUserRefreshTokenInfo(platform, userId).map(textCipher::decrypt);
    }

    @Override
    public void deleteUserToken(@NonNull Platform platform, @NonNull String userId) {
        userTokenRepository.deleteByPlatformAndUserIdentityId(platform, userId);
    }

    @Override
    public void setUserToken(@NonNull TokenWithIdentity tokenWithIdentity) {

        final var identity = userIdentityRepository.findByIdentification(tokenWithIdentity.identity()).orElse(null);
        final var encrypted = tokenWithIdentity.getEncryptedToken(textCipher);

        if (identity == null) {
            newUserIdentityWithToken(tokenWithIdentity.userInfo(), encrypted);
        } else {
            updateUserIdentityWithToken(identity, encrypted);
        }

    }

    private void updateUserIdentityWithToken(@NonNull UserIdentityEntity userIdentity, @NonNull UserToken.Encrypted userToken) {
        userIdentity.updateToken(userToken);
        userIdentityRepository.update(userIdentity);
    }

    private void newUserIdentityWithToken(@NonNull UserInfo userInfo, @NonNull UserToken.Encrypted userToken) {
        final var userIdentity = UserIdentityEntity.createWith(userInfo, userTypeProvider.getUserType(userInfo));
        userIdentity.updateToken(userToken);
        userIdentityRepository.save(userIdentity);
    }
}
