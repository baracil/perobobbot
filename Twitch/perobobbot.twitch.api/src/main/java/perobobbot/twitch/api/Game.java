package perobobbot.twitch.api;

import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;

@Serdeable
public record Game(@NonNull String name, @NonNull String id) {
}
