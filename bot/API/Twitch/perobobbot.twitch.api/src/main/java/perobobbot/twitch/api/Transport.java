package perobobbot.twitch.api;

import io.micronaut.core.annotation.Introspected;
import jakarta.annotation.Nullable;
import lombok.Value;

import java.util.Optional;

@Value
@Introspected
public class Transport {
    String method;
    String callback;
    @Nullable String secret;

    public Optional<String> getSecret() {
        return Optional.ofNullable(secret);
    }
}
