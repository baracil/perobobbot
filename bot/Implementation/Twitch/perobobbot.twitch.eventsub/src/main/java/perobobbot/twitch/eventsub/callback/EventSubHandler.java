package perobobbot.twitch.eventsub.callback;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import lombok.NonNull;

/**
 * Marker interface to differentiate callback for event sub from other callback
 */
public interface EventSubHandler {

    int priority();

    @NonNull HttpResponse<?> handleCall(@NonNull HttpRequest<?> request, @NonNull String body, @NonNull CallContext context);
}
