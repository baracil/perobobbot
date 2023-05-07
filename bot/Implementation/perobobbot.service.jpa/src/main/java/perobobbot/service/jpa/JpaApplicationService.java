package perobobbot.service.jpa;

import fpc.tools.cipher.TextCipher;
import fpc.tools.lang.Secret;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.api.data.Application;
import perobobbot.api.data.ApplicationToken;
import perobobbot.api.data.CreateApplicationParameter;
import perobobbot.api.data.Platform;
import perobobbot.api.plugin.PerobobbotService;
import perobobbot.domain.jpa.entity.ApplicationEntity;
import perobobbot.domain.jpa.entity.ApplicationTokenEntity;
import perobobbot.domain.jpa.repository.ApplicationRepository;
import perobobbot.domain.jpa.repository.ApplicationTokenRepository;
import perobobbot.service.api.ApplicationService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Singleton
@RequiredArgsConstructor
@Transactional
@PerobobbotService(apiVersion = ApplicationService.VERSION,serviceType = ApplicationService.class)
public class JpaApplicationService implements ApplicationService {

    private final @NonNull
    @Inject ApplicationRepository applicationRepository;
    private final @NonNull
    @Inject ApplicationTokenRepository applicationTokenRepository;
    private final @NonNull
    @Named("Db") TextCipher textCipher;

    @Override
    public Optional<Application.Decrypted> findApplication(Platform platform) {
        return applicationRepository.findByPlatform(platform)
                                    .map(ApplicationEntity::toView)
                                    .map(textCipher::decrypt);
    }

    @Override
    public Optional<ApplicationToken.Decrypted> findApplicationToken(Platform platform) {
        return applicationTokenRepository.findByApplicationPlatform(platform)
                                         .map(ApplicationTokenEntity::toView)
                                         .map(textCipher::decrypt);
    }

    @Override
    public String getApplicationClientId(Platform platform) {
        return applicationRepository.getClientIdByPlatform(platform);
    }

    @Override
    public Application.Decrypted saveApplication(Platform platform, CreateApplicationParameter parameter) {
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
    public List<Platform> getAllPlatforms() {
        return applicationRepository.findPlatform().toList();
    }

    @Override
    public ApplicationToken.Decrypted saveApplicationToken(ApplicationToken<Secret> applicationToken) {
        final var application = applicationRepository.getByPlatform(applicationToken.platform());
        final var token = application.setToken(textCipher.encrypt(applicationToken.accessToken()), applicationToken.expirationInstant());

        return applicationTokenRepository.save(token).toView().decrypt(textCipher);
    }
}
