package perobobbot.web.controller;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import lombok.RequiredArgsConstructor;
import perobobbot.api.data.Platform;
import perobobbot.service.api.ApplicationService;
import perobobbot.web.api.ClientApi;

@Controller(ClientApi.PATH)
@RequiredArgsConstructor
@ExecuteOn(TaskExecutors.IO)
public class ClientController implements ClientApi {

    private final ApplicationService applicationService;

    @Override
    public String getApplicationClientId(@PathVariable Platform platform) {
        return applicationService.getApplicationClientId(platform);
    }
}
