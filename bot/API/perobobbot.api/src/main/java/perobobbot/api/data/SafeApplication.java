package perobobbot.api.data;

import io.micronaut.core.annotation.Introspected;

@Introspected
public record SafeApplication(Platform platform, String name, String clientId) {
}
