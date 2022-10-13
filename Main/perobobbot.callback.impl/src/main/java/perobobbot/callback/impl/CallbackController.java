package perobobbot.callback.impl;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.callback.api.CallbackManager;
import perobobbot.web.api.WebService;

@RequiredArgsConstructor
@Controller(WebService.ROOT_PATH + "/callback")
public class CallbackController implements WebService {

    private final @NonNull CallbackManager callbackManager;

    @Get("/{id}")
    public HttpResponse<?> get(@NonNull String id, @NonNull HttpRequest<?> request) {
        System.out.println("GET");
        return callbackManager.findCallback(id)
                              .map(c -> c.handleCall(request))
                              .orElseGet(HttpResponse::notFound);
    }


}
