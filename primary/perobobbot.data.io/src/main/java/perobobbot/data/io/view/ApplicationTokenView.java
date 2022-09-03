package perobobbot.data.io.view;

import fpc.tools.lang.Secret;
import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;

import java.time.Instant;

@Serdeable
public record ApplicationTokenView(@NonNull String platform,
                                   @NonNull Secret accessToken,
                                   @NonNull Instant expirationInstant) {

}
