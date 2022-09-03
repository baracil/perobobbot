package perobobbot.data.io.view;

import fpc.tools.lang.Secret;
import lombok.NonNull;

import java.time.Instant;
import java.util.List;

public record UserTokenView(@NonNull String platform,
                            @NonNull Secret accessToken,
                            @NonNull Secret refreshToken,
                            @NonNull Instant expiringInstant,
                            @NonNull List<String> scopes,
                            @NonNull String tokenType) {

}
