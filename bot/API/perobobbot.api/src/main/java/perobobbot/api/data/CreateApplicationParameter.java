package perobobbot.api.data;

import fpc.tools.lang.Secret;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;

@Introspected
@Serdeable
public record CreateApplicationParameter(@NonNull String name,
                                         @NonNull String clientId,
                                         @NonNull Secret clientSecret) {

}
