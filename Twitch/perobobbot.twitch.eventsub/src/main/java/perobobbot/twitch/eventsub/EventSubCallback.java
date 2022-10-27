package perobobbot.twitch.eventsub;

import fpc.tools.lang.Secret;
import fpc.tools.lang.Todo;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.callback.api.Callback;

@RequiredArgsConstructor
public class EventSubCallback implements Callback {

    private final @NonNull Secret secret;

    @Override
    public @NonNull HttpResponse<?> handleCall(@NonNull HttpRequest<?> request) {
        return Todo.TODO();
    }
}
