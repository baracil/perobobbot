package perobobbot.data.web.controller;

import fpc.tools.cipher.TextEncryptor;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.PathVariable;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import jakarta.inject.Inject;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.data.io.CreateApplicationParameter;
import perobobbot.data.io.CreateApplicationTokenParameter;
import perobobbot.data.io.Platform;
import perobobbot.data.io.view.ApplicationTokenView;
import perobobbot.data.io.view.ApplicationView;
import perobobbot.data.io.view.EncryptedApplicationView;
import perobobbot.data.service.api.ApplicationService;
import perobobbot.data.web.api.ApplicationApi;

import java.util.Base64;
import java.util.Optional;

@OpenAPIDefinition(
        info = @Info(
                title = "perobobbot",
                version = "0.0"
        )
)
@Controller(value = ApplicationApi.PATH)
@RequiredArgsConstructor
public class ApplicationController implements ApplicationApi {

    private final @NonNull
    @Inject ApplicationService applicationService;

    @Override
    public @NonNull ApplicationView getApplication(@NonNull Platform platform) {
        return applicationService.getApplication(platform);
    }

    @Override
    public @NonNull String getApplicationClientId(@NonNull Platform platform) {
        return applicationService.getApplication(platform).clientId();
    }

    @Override
    public @NonNull EncryptedApplicationView getApplicationEncrypted(@NonNull Platform platform, String base64EncodePublicKey) {
        final var applicationView = applicationService.getApplication(platform);
        return applicationView.encrypt(TextEncryptor.createRSA(Base64.getDecoder().decode(base64EncodePublicKey)));
    }

    @Override
    public @NonNull ApplicationView createApplication(@NonNull @PathVariable Platform platform,
                                                      @NonNull @Body CreateApplicationParameter parameter) {
        return applicationService.saveApplication(platform, parameter);
    }

    @Override
    public @NonNull Optional<ApplicationTokenView> findApplicationTokenView(@NonNull Platform platform) {
        return applicationService.findApplicationToken(platform);
    }

    @Override
    public @NonNull ApplicationTokenView createApplicationTokenView(@NonNull Platform platform,
                                                                    @NonNull CreateApplicationTokenParameter parameter) {
        return applicationService.saveApplicationToken(platform,parameter);
    }
}
