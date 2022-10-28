package perobobbot.api.data;

import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;

@Serdeable
public record SafeApplication(@NonNull Platform platform, @NonNull String name, @NonNull String clientId) {
}
