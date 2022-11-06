package perobobbot.twitch.api;

import io.micronaut.core.annotation.Introspected;
import lombok.NonNull;
import lombok.Value;

import java.util.Optional;

@Value
@Introspected
public class Transport {
    @NonNull String method;
    @NonNull String callback;
    String secret;

    public @NonNull Optional<String> getSecret() {
        return Optional.ofNullable(secret);
    }
}
