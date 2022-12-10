package perobobbot.api.bus;

import io.micronaut.core.annotation.NonNull;

public interface Bus extends EventDispatcher {

    int VERSION = 1;

    @NonNull
    void publishEvent(@NonNull String topic, @NonNull Event event);
}
