package perobobbot.api.bus;

import io.micronaut.core.annotation.NonNull;

public interface Bus {

    @NonNull
    void publishEvent(@NonNull Event event);
}
