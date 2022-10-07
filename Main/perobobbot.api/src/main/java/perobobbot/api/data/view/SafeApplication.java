package perobobbot.api.data.view;

import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;
import perobobbot.api.data.Platform;

@Serdeable
public record SafeApplication(@NonNull Platform platform, @NonNull String name, @NonNull String clientId) {
}
