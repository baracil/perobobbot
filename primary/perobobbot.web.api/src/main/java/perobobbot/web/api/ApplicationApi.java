package perobobbot.web.api;

import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Put;
import lombok.NonNull;
import perobobbot.api.data.CreateApplicationParameter;
import perobobbot.api.data.Platform;

public interface ApplicationApi extends WebService {

    String PATH = ROOT_PATH+"/data/application";

    @Get("/{platform}/clientId")
    @NonNull String getApplicationClientId(@NonNull @PathVariable Platform platform);

    @Put("/{platform}")
    void createApplication(@NonNull @PathVariable Platform platform, @NonNull @Body CreateApplicationParameter parameter);


}
