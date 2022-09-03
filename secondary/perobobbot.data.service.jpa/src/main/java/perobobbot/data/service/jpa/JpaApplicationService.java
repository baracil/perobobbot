package perobobbot.data.service.jpa;

import fpc.tools.cipher.TextCipher;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.data.io.CreateApplicationParameter;
import perobobbot.data.io.CreateApplicationTokenParameter;
import perobobbot.data.io.view.ApplicationTokenView;
import perobobbot.data.io.view.ApplicationView;
import perobobbot.data.service.api.ApplicationService;
import perobobbot.data.service.jpa.domain.Application;
import perobobbot.data.service.jpa.repository.ApplicationRepository;
import perobobbot.data.service.jpa.repository.ApplicationTokenRepository;

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
    @Named("dbCipher") TextCipher textCipher;

    @Override
    public @NonNull Optional<ApplicationView> findApplication(@NonNull String platform) {
        return applicationRepository.findByPlatform(platform).map(a -> a.toView(textCipher));
    }

    @Override
    public @NonNull Optional<ApplicationTokenView> findApplicationToken(@NonNull String platform) {
        return applicationTokenRepository.findByApplicationPlatform(platform).map(token -> token.toView(textCipher));
    }

    @Override
    public @NonNull ApplicationView saveApplication(@NonNull String platform, @NonNull CreateApplicationParameter parameter) {
        final var existing = applicationRepository.findByPlatform(platform).orElse(null);
        final var name = parameter.name();
        final var clientId = parameter.clientId();
        final var secret = textCipher.encrypt(parameter.clientSecret());

        if (existing == null) {
            return applicationRepository.save(new Application(platform, name, clientId, secret)).toView(textCipher);
        } else {
            existing.setName(name);
            existing.setClientId(clientId);
            existing.setClientSecret(secret);
            return applicationRepository.update(existing).toView(textCipher);
        }
    }

    @Override
    public @NonNull ApplicationTokenView saveApplicationToken(@NonNull String platform, @NonNull CreateApplicationTokenParameter parameter) {
        final var application = applicationRepository.getByPlatform(platform);
        final var token = application.setToken(textCipher.encrypt(parameter.accessToken()), parameter.expirationInstant());

        return applicationTokenRepository.save(token).toView(textCipher);
    }
}
