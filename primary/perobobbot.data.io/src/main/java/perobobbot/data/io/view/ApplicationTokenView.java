package perobobbot.data.io.view;

import fpc.tools.cipher.TextEncryptor;
import fpc.tools.lang.Secret;
import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;
import perobobbot.data.io.Platform;

import java.time.Instant;

@Serdeable
public record ApplicationTokenView(@NonNull Platform platform,
                                   @NonNull Secret accessToken,
                                   @NonNull Instant expirationInstant) {

    public @NonNull EncryptedApplicationTokenView encrypt(@NonNull TextEncryptor textEncryptor) {
        return new EncryptedApplicationTokenView(
                platform,
                textEncryptor.encrypt(accessToken),
                expirationInstant
        );
    }

}
