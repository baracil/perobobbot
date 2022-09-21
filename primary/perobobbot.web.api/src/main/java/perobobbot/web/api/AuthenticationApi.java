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

public interface AuthenticationApi extends WebService {

    String PATH = ROOT_PATH+"/oauth";


    @Get("/{platform}/user/uri")
    @NonNull URI startAuthorizationCodeGrantFlow(@NonNull @PathVariable Platform platform);

    @Get("/{platform}/user/launch")
    void launchAuthorizationCodeGrantFlow(@NonNull @PathVariable Platform platform) throws IOException;

    @Get("/{platform}/user")
    @NonNull HttpResponse<?> authenticate(@NonNull @PathVariable String platform);

    @Get("/callback/{platform}{?values*}")
    void getCallback(@NonNull @PathVariable Platform platform,
                            @NonNull @QueryValue("values") Map<String, String> values);



}
