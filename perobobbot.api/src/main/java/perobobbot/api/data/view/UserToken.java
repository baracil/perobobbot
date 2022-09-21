package perobobbot.api.data.view;

import fpc.tools.cipher.Encryptable;
import fpc.tools.cipher.TextEncryptor;
import fpc.tools.lang.Secret;
import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;
import perobobbot.api.data.Platform;

import java.time.Instant;
import java.util.List;

@Serdeable
public record UserToken(@NonNull Platform platform,
                        @NonNull Secret accessToken,
                        @NonNull Secret refreshToken,
                        @NonNull Instant expiringInstant,
                        @NonNull List<String> scopes,
                        @NonNull String tokenType) implements Encryptable<EncryptedUserToken> {

    public @NonNull EncryptedUserToken encrypt(@NonNull TextEncryptor textEncryptor) {
        return new EncryptedUserToken(platform,
                textEncryptor.encrypt(accessToken),
                textEncryptor.encrypt(refreshToken),
                expiringInstant,
                scopes,
                tokenType);
    }

}
