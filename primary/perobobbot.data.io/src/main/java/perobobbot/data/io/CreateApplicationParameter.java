package perobobbot.data.io;

import fpc.tools.lang.Secret;
import lombok.NonNull;

public record CreateApplicationParameter(@NonNull String name,
                                         @NonNull String clientId,
                                         @NonNull Secret clientSecret) {
}
