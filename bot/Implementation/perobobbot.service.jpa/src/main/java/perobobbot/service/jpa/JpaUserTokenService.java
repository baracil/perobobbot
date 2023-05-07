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

    private final UserTypeProvider userTypeProvider;
    private final UserIdentityRepository userIdentityRepository;
    private final UserTokenRepository userTokenRepository;
    private final @NonNull
    @Named("Db") TextCipher textCipher;

    @Override
    public Optional<UserToken.Decrypted> findUserToken(Platform platform, String userId) {
        return userTokenRepository.findByPlatformAndPlatformUserId(platform, userId).map(UserTokenEntity::toView).map(textCipher::decrypt);
    }

    @Override
    public Optional<RefreshTokenInfo<Secret>> findUserRefreshToken(Platform platform, String userId) {
        return userTokenRepository.findUserRefreshTokenInfo(platform, userId).map(textCipher::decrypt);
    }

    @Override
    public void deleteUserToken(Platform platform, String userId) {
        userTokenRepository.deleteByPlatformAndUserIdentityId(platform, userId);
    }

    @Override
    public void setUserToken(TokenWithIdentity tokenWithIdentity) {

        final var identity = userIdentityRepository.findByIdentification(tokenWithIdentity.identity()).orElse(null);
        final var encrypted = tokenWithIdentity.getEncryptedToken(textCipher);

        if (identity == null) {
            newUserIdentityWithToken(tokenWithIdentity.userInfo(), encrypted);
        } else {
            updateUserIdentityWithToken(identity, encrypted);
        }

    }

    private void updateUserIdentityWithToken(UserIdentityEntity userIdentity, UserToken.Encrypted userToken) {
        userIdentity.updateToken(userToken);
        userIdentityRepository.update(userIdentity);
    }

    private void newUserIdentityWithToken(UserInfo userInfo, UserToken.Encrypted userToken) {
        final var userIdentity = UserIdentityEntity.createWith(userInfo, userTypeProvider.getUserType(userInfo));
        userIdentity.updateToken(userToken);
        userIdentityRepository.save(userIdentity);
    }
}
