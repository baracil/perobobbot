package perobobbot.service.jpa.service;

import com.google.common.collect.ImmutableList;
import fpc.tools.cipher.TextCipher;
import fpc.tools.lang.Secret;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.api.data.CreateApplicationParameter;
import perobobbot.api.data.Platform;
import perobobbot.api.data.view.Application;
import perobobbot.api.data.view.ApplicationToken;
import perobobbot.api.plugin.PerobobbotService;
import perobobbot.api.plugin.PerobobbotServices;
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
@PerobobbotServices({@PerobobbotService(apiVersion = 1,serviceType = ApplicationService.class)})
public class JpaApplicationService implements ApplicationService {

    private final @NonNull
    @Inject ApplicationRepository applicationRepository;
    private final @NonNull
    @Inject ApplicationTokenRepository applicationTokenRepository;
    private final @NonNull
    @Named("Db") TextCipher textCipher;

    @Override
    public @NonNull Optional<Application.Decrypted> findApplication(@NonNull Platform platform) {
        return applicationRepository.findByPlatform(platform)
                                    .map(ApplicationEntity::toView)
                                    .map(textCipher::decrypt);
    }

    @Override
    public @NonNull Optional<ApplicationToken.Decrypted> findApplicationToken(@NonNull Platform platform) {
        return applicationTokenRepository.findByApplicationPlatform(platform)
                                         .map(ApplicationTokenEntity::toView)
                                         .map(textCipher::decrypt);
    }

    @Override
    public @NonNull String getApplicationClientId(@NonNull Platform platform) {
        return applicationRepository.getClientIdByPlatform(platform);
    }

    @Override
    public @NonNull Application.Decrypted saveApplication(@NonNull Platform platform, @NonNull CreateApplicationParameter parameter) {
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
    public @NonNull ImmutableList<Platform> getAllPlatforms() {
        return applicationRepository.findPlatform().collect(ImmutableList.toImmutableList());
    }

    @Override
    public @NonNull ApplicationToken.Decrypted saveApplicationToken(@NonNull ApplicationToken<Secret> applicationToken) {
        final var application = applicationRepository.getByPlatform(applicationToken.platform());
        final var token = application.setToken(textCipher.encrypt(applicationToken.accessToken()), applicationToken.expirationInstant());

        return applicationTokenRepository.save(token).toView().decrypt(textCipher);
    }
}
