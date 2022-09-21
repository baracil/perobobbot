package perobobbot.api.data.view;

import fpc.tools.cipher.Decryptable;
import fpc.tools.cipher.TextDecryptor;
import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;
import perobobbot.api.data.Platform;

import java.time.Instant;
import java.util.List;

@Serdeable
public record EncryptedUserToken(@NonNull Platform platform,
                                 @NonNull String accessToken,
                                 @NonNull String refreshToken,
                                 @NonNull Instant expiringInstant,
                                 @NonNull List<String> scopes,
                                 @NonNull String tokenType) implements Decryptable<UserToken> {

    public @NonNull UserToken decrypt(@NonNull TextDecryptor textDecryptor) {
        return new UserToken(platform,
                textDecryptor.decrypt(accessToken),
                textDecryptor.decrypt(refreshToken),
                expiringInstant,
                scopes,
                tokenType);
    }

}
