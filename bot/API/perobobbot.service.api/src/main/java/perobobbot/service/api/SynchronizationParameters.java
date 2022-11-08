package perobobbot.service.api;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;
import lombok.Value;
import perobobbot.api.data.Platform;

import java.util.Optional;

@Value
@Serdeable
public class SynchronizationParameters {
    @Nullable Platform platform;

    public @NonNull Optional<Platform> getPlatform() {
        return Optional.ofNullable(platform);
    }
}
