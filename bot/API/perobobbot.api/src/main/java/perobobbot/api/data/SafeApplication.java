package perobobbot.api.data;

import io.micronaut.core.annotation.Introspected;
import lombok.NonNull;

@Introspected
public record SafeApplication(@NonNull Platform platform, @NonNull String name, @NonNull String clientId) {
}
