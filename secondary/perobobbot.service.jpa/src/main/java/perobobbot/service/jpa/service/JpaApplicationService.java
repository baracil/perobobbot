package perobobbot.service.jpa.service;

import fpc.tools.cipher.TextCipher;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.api.data.CreateApplicationParameter;
import perobobbot.api.data.CreateApplicationTokenParameter;
import perobobbot.api.data.Platform;
import perobobbot.api.data.view.Application;
import perobobbot.api.data.view.ApplicationToken;
import perobobbot.service.api.ApplicationService;
import perobobbot.service.jpa.domain.ApplicationEntity;
import perobobbot.service.jpa.domain.ApplicationTokenEntity;
import perobobbot.service.jpa.repository.ApplicationRepository;
import perobobbot.service.jpa.repository.ApplicationTokenRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Singleton
@RequiredArgsConstructor
@Transactional
public class JpaApplicationService implements ApplicationService {

    private final @NonNull
    @Inject ApplicationRepository applicationRepository;
    private final @NonNull
    @Inject ApplicationTokenRepository applicationTokenRepository;
    private final @NonNull
    @Named("Db") TextCipher textCipher;

    @Override
    public @NonNull Optional<Application> findApplication(@NonNull Platform platform) {
        return applicationRepository.findByPlatform(platform)
                                    .map(ApplicationEntity::toView)
                                    .map(textCipher::decrypt);
    }

    @Override
    public @NonNull Optional<ApplicationToken> findApplicationToken(@NonNull Platform platform) {
        return applicationTokenRepository.findByApplicationPlatform(platform)
                                         .map(ApplicationTokenEntity::toView)
                                         .map(textCipher::decrypt);
    }

    @Override
    public @NonNull String getApplicationClientId(@NonNull Platform platform) {
        return applicationRepository.getClientIdByPlatform(platform);
    }

    @Override
    public @NonNull Application saveApplication(@NonNull Platform platform, @NonNull CreateApplicationParameter parameter) {
        final var existing = applicationRepository.findByPlatform(platform).orElse(null);
        final var name = parameter.name();
        final var clientId = parameter.clientId();
        final var secret = textCipher.encrypt(parameter.clientSecret());

        final ApplicationEntity applicationEntity;
        if (existing == null) {
            applicationEntity = applicationRepository.save(new ApplicationEntity(platform, name, clientId, secret));
        } else {
            existing.setName(name);
            existing.setClientId(clientId);
            existing.setClientSecret(secret);
            applicationEntity = applicationRepository.update(existing);
        }
        return applicationEntity.toView().decrypt(textCipher);
    }

    @Override
    public @NonNull ApplicationToken saveApplicationToken(@NonNull Platform platform, @NonNull CreateApplicationTokenParameter parameter) {
        final var application = applicationRepository.getByPlatform(platform);
        final var token = application.setToken(textCipher.encrypt(parameter.accessToken()), parameter.expirationInstant());

        return applicationTokenRepository.save(token).toView().decrypt(textCipher);
    }
}
