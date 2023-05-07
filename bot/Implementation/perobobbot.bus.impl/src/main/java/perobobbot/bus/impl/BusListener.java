package perobobbot.bus.impl;

import perobobbot.bus.api.Message;

public interface BusListener<T> {

    int DEFAULT_PRIORITY = 500;

    default int priority() {
        return DEFAULT_PRIORITY;
    }

    void onBusEvent(Message<T> message) throws Exception;
}
