package perobobbot.api.bus;

import lombok.NonNull;

import java.util.Optional;

/**
 * Marker interface for all events in Perobobbot
 */
public interface Event {

    default <T> @NonNull Optional<T> as(@NonNull Class<T> type) {
        return Optional.ofNullable(type.isInstance(this) ? type.cast(this) : null);
    }
}
