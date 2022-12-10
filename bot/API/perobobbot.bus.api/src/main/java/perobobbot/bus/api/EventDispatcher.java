package perobobbot.bus.api;

import fpc.tools.lang.Subscription;
import lombok.NonNull;

public interface EventDispatcher {
    int VERSION = 1;

    /**
     * Add a listener ot the bus
     *
     * @param topic topic
     * @param eventType the type of event the listener listen to
     * @param listener  the listener
     * @param <T>       the type of the event
     * @return a subscription that can be used to remove the listener
     */
    <T> Subscription addListener(@NonNull String topic, @NonNull Class<T> eventType, @NonNull BusListener<? super T> listener);
}
