package perobobbot.api.data;

import fpc.tools.lang.Secret;
import io.micronaut.core.annotation.Introspected;
import lombok.NonNull;

@Introspected
public record CreateApplicationParameter(@NonNull String name,
                                         @NonNull String clientId,
                                         @NonNull Secret clientSecret) {

}
