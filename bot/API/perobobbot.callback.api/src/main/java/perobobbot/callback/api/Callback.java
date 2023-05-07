package perobobbot.callback.api;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;

public interface Callback {

    HttpResponse<?> handleCall(HttpRequest<?> request, String body);
}
