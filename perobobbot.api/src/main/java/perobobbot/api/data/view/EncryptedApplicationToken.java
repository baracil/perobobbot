package perobobbot.api.data.view;

import fpc.tools.cipher.Decryptable;
import fpc.tools.cipher.TextDecryptor;
import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;
import perobobbot.api.data.Platform;

import java.time.Instant;

@Serdeable
public record EncryptedApplicationToken(@NonNull Platform platform,
                                        @NonNull String accessToken,
                                        @NonNull Instant expirationInstant) implements Decryptable<ApplicationToken> {

    public @NonNull ApplicationToken decrypt(@NonNull TextDecryptor textDecryptor) {
        return new ApplicationToken(platform, textDecryptor.decrypt(accessToken), expirationInstant);
    }

}
