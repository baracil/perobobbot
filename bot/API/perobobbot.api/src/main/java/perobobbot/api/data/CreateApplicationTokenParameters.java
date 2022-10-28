package perobobbot.api.data;

import fpc.tools.lang.Secret;
import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;

import java.time.Instant;

@Serdeable
public record CreateApplicationTokenParameters(
        @NonNull Secret accessToken,
        @NonNull Instant expirationInstant) {

}
