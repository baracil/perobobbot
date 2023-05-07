package perobobbot.web.api;

import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Put;
import perobobbot.api.data.CreateApplicationParameter;
import perobobbot.api.data.Platform;
import perobobbot.api.data.SafeApplication;

import java.util.List;

public interface PlatformApi extends WebService {

    String PATH = ROOT_PATH+"/data/platform";

    @Get
    List<Platform> getAllPlatforms();

    @Get("/{platform}")
    SafeApplication getApplication(@PathVariable Platform platform);

    @Put("/{platform}")
    void createApplication(@PathVariable Platform platform, @Body CreateApplicationParameter parameter);

}
