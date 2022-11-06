package perobobbot.api.bus.fallback;

import io.micronaut.context.event.ApplicationEventPublisher;
import io.micronaut.retry.annotation.Fallback;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.api.bus.Bus;
import perobobbot.api.bus.Event;

@RequiredArgsConstructor
@Fallback
public class FallbackBus implements Bus {

    private final @NonNull ApplicationEventPublisher publisher;

    @Override
    public void publishEvent(@NonNull Event event) {
        publisher.publishEventAsync(event);
    }
}
