package perobobbot.callback.api;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import lombok.NonNull;

public interface Callback {

    @NonNull HttpResponse<?> handleCall(@NonNull HttpRequest<?> request, @NonNull String body);
}
