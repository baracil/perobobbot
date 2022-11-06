package perobobbot.api.data;

import fpc.tools.lang.Secret;
import io.micronaut.core.annotation.Introspected;
import lombok.NonNull;

import java.time.Instant;

@Introspected
public record CreateApplicationTokenParameters(
        @NonNull Secret accessToken,
        @NonNull Instant expirationInstant) {

}
