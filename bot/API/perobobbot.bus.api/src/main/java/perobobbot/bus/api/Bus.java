package perobobbot.bus.api;

import lombok.NonNull;
import perobobbot.api.Event;

public interface Bus extends EventDispatcher {

    int VERSION = 1;

    void publishEvent(@NonNull String topic, @NonNull Event event);
}
