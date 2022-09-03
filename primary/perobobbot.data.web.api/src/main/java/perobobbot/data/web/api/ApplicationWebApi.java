package perobobbot.data.web.api;

import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Put;
import lombok.NonNull;
import perobobbot.data.io.CreateApplicationParameter;
import perobobbot.data.io.CreateApplicationTokenParameter;
import perobobbot.data.io.view.ApplicationTokenView;
import perobobbot.data.io.view.ApplicationView;

import java.util.Optional;

public interface ApplicationWebApi extends WebService {

    String PATH = "/api/v1/application";

    /**
     * Retrieve the application for the given platform if any exists
     * @param platform the name of the platform
     * @return an optional containing the application
     */
    @Get("/{platform}")
    @NonNull Optional<ApplicationView> getApplication(@NonNull @PathVariable String platform);

    @Put("/{platform}")
    @NonNull ApplicationView createApplication(@NonNull @PathVariable String platform, @NonNull @Body CreateApplicationParameter parameter);

    @Get("/{platform}/token")
    @NonNull Optional<ApplicationTokenView> getApplicationTokenView(@NonNull @PathVariable String platform);

    @Put("/{platform}/token")
    @NonNull ApplicationTokenView createApplicationTokenView(@NonNull @PathVariable String platform, @NonNull @Body CreateApplicationTokenParameter parameters);
}
