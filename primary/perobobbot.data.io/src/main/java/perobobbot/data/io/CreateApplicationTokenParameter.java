package perobobbot.data.io;

import fpc.tools.lang.Secret;
import lombok.NonNull;

import java.time.Instant;

public record CreateApplicationTokenParameter(
        @NonNull Secret accessToken,
        @NonNull Instant expirationInstant) {
}
