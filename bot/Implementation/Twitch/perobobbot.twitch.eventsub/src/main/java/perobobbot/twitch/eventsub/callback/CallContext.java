package perobobbot.twitch.eventsub.callback;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;

public interface CallContext {

    HttpResponse<?> proceed(HttpRequest<?> request, String body);
}
