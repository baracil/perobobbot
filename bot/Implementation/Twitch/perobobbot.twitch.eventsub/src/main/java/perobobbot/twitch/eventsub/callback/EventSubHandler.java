package perobobbot.twitch.eventsub.callback;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;

/**
 * Marker interface to differentiate callback for event sub from other callback
 */
public interface EventSubHandler {

    int priority();

    HttpResponse<?> handleCall(HttpRequest<?> request, String body, CallContext context);
}
