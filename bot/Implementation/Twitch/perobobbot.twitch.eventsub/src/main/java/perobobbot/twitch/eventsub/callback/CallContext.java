package perobobbot.twitch.eventsub.callback;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import lombok.NonNull;

public interface CallContext {

    @NonNull HttpResponse<?> proceed(@NonNull HttpRequest<?> request);
}
