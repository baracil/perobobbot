package perobobbot.service.jpa.service;

import fpc.tools.cipher.TextCipher;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.api.data.Platform;
import perobobbot.api.data.view.UserToken;
import perobobbot.service.api.UserTokenService;
import perobobbot.service.jpa.domain.UserTokenEntity;
import perobobbot.service.jpa.repository.UserTokenRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Singleton
@RequiredArgsConstructor
@Transactional
public class JpaUserTokenService implements UserTokenService  {

    private final @NonNull UserTokenRepository userTokenRepository;
    private final @NonNull @Named("Db") TextCipher textCipher;

    @Override
    public @NonNull Optional<UserToken> findUserToken(@NonNull Platform platform) {
        return userTokenRepository.findByPlatform(platform).map(UserTokenEntity::toView).map(v -> v.decrypt(textCipher));
    }

    @Override
    public void deleteUserToken(@NonNull Platform platform) {
        userTokenRepository.deleteByPlatform(platform);
    }

    @Override
    public void setUserToken(@NonNull UserToken token) {
        final var encryptedToken = token.encrypt(textCipher);
        final var existing = userTokenRepository.findByPlatform(token.platform()).orElse(null);
        if (existing != null) {
            existing.updateWith(encryptedToken);
            userTokenRepository.update(existing);
        } else {
            userTokenRepository.save(UserTokenEntity.createWith(encryptedToken));
        }


    }
}
