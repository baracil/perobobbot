package perobobbot.api.data;

import fpc.tools.lang.Secret;
import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;

@Serdeable
public record CreateApplicationParameter(@NonNull String name,
                                         @NonNull String clientId,
                                         @NonNull Secret clientSecret) {

}
