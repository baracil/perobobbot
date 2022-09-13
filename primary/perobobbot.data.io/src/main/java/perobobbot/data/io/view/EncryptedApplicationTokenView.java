package perobobbot.data.io.view;

import fpc.tools.cipher.TextDecryptor;
import fpc.tools.cipher.TextEncryptor;
import fpc.tools.lang.Secret;
import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;
import perobobbot.data.io.Platform;

import java.time.Instant;

@Serdeable
public record EncryptedApplicationTokenView(@NonNull Platform platform,
                                            @NonNull String accessToken,
                                            @NonNull Instant expirationInstant) {

    public @NonNull ApplicationTokenView decrypt(@NonNull TextDecryptor textDecryptor) {
        return new ApplicationTokenView(platform, textDecryptor.decrypt(accessToken), expirationInstant);
    }
}
