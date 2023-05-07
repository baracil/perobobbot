package perobobbot.callback.impl;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.http.context.ServerRequestContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import perobobbot.api.PerobobbotException;
import perobobbot.callback.api.CallbackManager;
import perobobbot.web.api.WebService;

@RequiredArgsConstructor
@Slf4j
@Controller(WebService.ROOT_PATH + "/callback")
public class CallbackController implements WebService {

    private final CallbackManager callbackManager;

    @Get("/{+path}")
    public HttpResponse<?> get(String path, @Body String body) {
        return handle(path,body);
    }

    @Post("/{+path}")
    public HttpResponse<?> post(String path, @Body String body) {
        return handle(path,body);
    }

    @Patch("/{+path}")
    public HttpResponse<?> patch(String path, @Body String body) {
        return handle(path,body);
    }

    @Delete("/{+path}")
    public HttpResponse<?> delete(String path, @Body String body) {
        return handle(path,body);
    }

    private HttpResponse<?> handle(String path, @Body String body) {
        final var id = extractId(path);
        HttpRequest<?> request = ServerRequestContext.currentRequest().orElseThrow(()-> new PerobobbotException("No request found"));
        return callbackManager.findCallback(id).map(c -> c.handleCall(request,body)).orElseGet(HttpResponse::notFound);
    }

    private String extractId(String path) {
        final var idx = path.indexOf("/");
        if (idx<0) {
            return path;
        }
        return path.substring(0,idx);
    }

}
