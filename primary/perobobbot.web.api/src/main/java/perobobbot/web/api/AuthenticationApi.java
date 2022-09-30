package perobobbot.web.api;


import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.QueryValue;
import lombok.NonNull;
import perobobbot.api.data.Platform;

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.Set;

public interface AuthenticationApi extends WebService {

    String PATH = ROOT_PATH+"/oauth";

    @Get("/platform")
    @NonNull Set<Platform> listManagedPlatforms();

    @Get("/user/{platform}/uri")
    @NonNull URI startAuthorizationCodeGrantFlow(@NonNull @PathVariable Platform platform);

    @Get("/user/{platform}/launch")
    void launchAuthorizationCodeGrantFlow(@NonNull @PathVariable Platform platform) throws IOException;

    @Get("/user/{platform}")
    @NonNull HttpResponse<?> authenticate(@NonNull @PathVariable Platform platform);

    @Get("/callback/{platform}{?values*}")
    void getCallback(@NonNull @PathVariable Platform platform,
                            @NonNull @QueryValue("values") Map<String, String> values);



}
