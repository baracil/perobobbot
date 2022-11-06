package perobobbot.api.bus;

import fpc.tools.lang.Subscription;
import lombok.NonNull;

public interface EventDispatcher {
    int VERSION = 1;

    <T> Subscription addListener(@NonNull Class<T> eventType, BusListener<? super T> listener);
}
