package perobobbot.web.controller;

import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import lombok.RequiredArgsConstructor;
import perobobbot.api.data.CreateApplicationParameter;
import perobobbot.api.data.Platform;
import perobobbot.api.data.SafeApplication;
import perobobbot.service.api.ApplicationService;
import perobobbot.web.api.PlatformApi;

import java.util.List;

@Controller(PlatformApi.PATH)
@RequiredArgsConstructor
@ExecuteOn(TaskExecutors.IO)
public class PlatformController implements PlatformApi {

    private final ApplicationService applicationService;

    @Override
    public List<Platform> getAllPlatforms() {
        return applicationService.getAllPlatforms();
    }

    @Override
    public SafeApplication getApplication(@PathVariable Platform platform) {
        return applicationService.getApplication(platform).toSafe();
    }

    @Override
    public void createApplication(@PathVariable Platform platform, @Body CreateApplicationParameter parameter) {
        applicationService.saveApplication(platform, parameter);
    }


}
