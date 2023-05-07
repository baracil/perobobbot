package perobobbot.api.data;

import fpc.tools.lang.Secret;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

@Introspected
@Serdeable
public record CreateApplicationParameter(String name,
                                         String clientId,
                                         Secret clientSecret) {

}
