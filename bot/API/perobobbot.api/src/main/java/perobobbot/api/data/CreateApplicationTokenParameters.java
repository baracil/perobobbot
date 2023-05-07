package perobobbot.api.data;

import fpc.tools.lang.Secret;
import io.micronaut.core.annotation.Introspected;

import java.time.Instant;

@Introspected
public record CreateApplicationTokenParameters(
        Secret accessToken,
        Instant expirationInstant) {

}
