package perobobbot.web.api;

import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Put;
import lombok.NonNull;
import perobobbot.api.data.CreateApplicationParameter;
import perobobbot.api.data.Platform;
import perobobbot.api.data.view.SafeApplication;

import java.util.List;

public interface PlatformApi extends WebService {

    String PATH = ROOT_PATH+"/data/platform";

    @Get
    @NonNull List<Platform> getAllPlatforms();

    @Get("/{platform}")
    @NonNull SafeApplication getApplication(@NonNull @PathVariable Platform platform);

    @Put("/{platform}")
    void createApplication(@NonNull @PathVariable Platform platform, @NonNull @Body CreateApplicationParameter parameter);

}
