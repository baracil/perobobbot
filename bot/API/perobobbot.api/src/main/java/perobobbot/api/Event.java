package perobobbot.api;

import java.util.Optional;

/**
 * Marker interface for all events in Perobobbot
 */
public interface Event {

    default <T> Optional<T> as(Class<T> type) {
        return Optional.ofNullable(type.isInstance(this) ? type.cast(this) : null);
    }
}
