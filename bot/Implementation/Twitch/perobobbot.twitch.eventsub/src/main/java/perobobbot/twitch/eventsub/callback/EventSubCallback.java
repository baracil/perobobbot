package perobobbot.twitch.eventsub.callback;

import com.google.common.collect.ImmutableList;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import lombok.NonNull;
import perobobbot.callback.api.Callback;

import java.util.Comparator;
import java.util.List;

public class EventSubCallback implements Callback {

    private final @NonNull ImmutableList<EventSubHandler> handlers;

    public EventSubCallback(@NonNull List<EventSubHandler> handlers) {
        this.handlers = handlers.stream()
                                .sorted(Comparator.comparingInt(EventSubHandler::priority))
                                .collect(ImmutableList.toImmutableList());
    }

    @Override
    public @NonNull HttpResponse<?> handleCall(@NonNull HttpRequest<?> request, @NonNull String body) {
        return new Context().proceed(request, body);
    }

    private class Context implements CallContext {
        private int index = 0;

        @Override
        public @NonNull HttpResponse<?> proceed(@NonNull HttpRequest<?> request, @NonNull String body) {
            if (index >= handlers.size()) {
                return HttpResponse.notFound();
            }
            final var handler = handlers.get(index);
            index++;
            return handler.handleCall(request, body, this);
        }
    }
}
