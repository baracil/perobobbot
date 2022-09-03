package perobobbot.data.io.view;

import fpc.tools.lang.Secret;
import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;

@Serdeable
public record ApplicationView(@NonNull String platform,
                              @NonNull String name,
                              @NonNull String clientId,
                              @NonNull Secret clientSecret) {


}
