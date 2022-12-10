package perobobbot.bus.api;

import lombok.NonNull;

public interface BusListener<T> {

    int DEFAULT_PRIORITY = 500;

    default int priority() {
        return DEFAULT_PRIORITY;
    }

    void onBusEvent(@NonNull T event) throws Exception;
}
