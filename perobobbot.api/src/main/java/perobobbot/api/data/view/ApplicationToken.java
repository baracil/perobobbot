package perobobbot.api.data.view;

import fpc.tools.cipher.Encryptable;
import fpc.tools.cipher.TextEncryptor;
import fpc.tools.lang.Secret;
import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;
import perobobbot.api.data.Platform;

import java.time.Instant;

@Serdeable
public record ApplicationToken(@NonNull Platform platform,
                               @NonNull Secret accessToken,
                               @NonNull Instant expirationInstant) implements Encryptable<EncryptedApplicationToken> {


    public @NonNull EncryptedApplicationToken encrypt(@NonNull TextEncryptor textEncryptor) {
        return new EncryptedApplicationToken(platform,textEncryptor.encrypt(accessToken),expirationInstant);
    }
}
