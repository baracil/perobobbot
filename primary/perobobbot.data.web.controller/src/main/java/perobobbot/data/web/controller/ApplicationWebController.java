package perobobbot.data.web.controller;

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
import perobobbot.data.io.view.ApplicationTokenView;
import perobobbot.data.io.view.ApplicationView;
import perobobbot.data.service.api.ApplicationService;
import perobobbot.data.web.api.ApplicationWebApi;

import java.util.Optional;

@OpenAPIDefinition(
        info = @Info(
                title = "perobobbot",
                version = "0.0"
        )
)
@Controller(value = ApplicationWebApi.PATH)
@RequiredArgsConstructor
public class ApplicationWebController implements ApplicationWebApi {

    private final @NonNull
    @Inject ApplicationService applicationService;

    @Override
    public @NonNull Optional<ApplicationView> getApplication(@NonNull String platform) {
        return applicationService.findApplication(platform);
    }

    @Override
    public @NonNull ApplicationView createApplication(@NonNull @PathVariable String platform,
                                                      @NonNull @Body CreateApplicationParameter parameter) {
        return applicationService.saveApplication(platform, parameter);
    }

    @Override
    public @NonNull Optional<ApplicationTokenView> getApplicationTokenView(@NonNull String platform) {
        return applicationService.findApplicationToken(platform);
    }

    @Override
    public @NonNull ApplicationTokenView createApplicationTokenView(@NonNull String platform,
                                                                    @NonNull CreateApplicationTokenParameter parameter) {
        return applicationService.saveApplicationToken(platform,parameter);
    }
}
