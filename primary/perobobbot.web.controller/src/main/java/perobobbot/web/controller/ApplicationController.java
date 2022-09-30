package perobobbot.web.controller;

import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.PathVariable;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.api.data.CreateApplicationParameter;
import perobobbot.api.data.Platform;
import perobobbot.service.api.ApplicationService;
import perobobbot.web.api.ApplicationApi;

@Controller(ApplicationApi.PATH)
@RequiredArgsConstructor
public class ApplicationController implements ApplicationApi {

    private final @NonNull ApplicationService applicationService;

    @Override
    public @NonNull String getApplicationClientId(@NonNull @PathVariable Platform platform) {
        return applicationService.getApplication(platform).clientId();
    }

    @Override
    public void createApplication(@NonNull @PathVariable Platform platform, @NonNull @Body CreateApplicationParameter parameter) {
        applicationService.saveApplication(platform, parameter);
    }


}
