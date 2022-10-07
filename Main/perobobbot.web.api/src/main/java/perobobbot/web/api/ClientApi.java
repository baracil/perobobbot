package perobobbot.web.api;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import lombok.NonNull;
import perobobbot.api.data.Platform;

public interface ClientApi extends WebService{

    String PATH = ROOT_PATH+"/data/client";

    @Get("/{platform}/id")
    @NonNull String getApplicationClientId(@NonNull @PathVariable Platform platform);


}
