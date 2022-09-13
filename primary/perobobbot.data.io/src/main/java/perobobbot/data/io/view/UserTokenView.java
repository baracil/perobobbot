package perobobbot.data.io.view;

import fpc.tools.cipher.TextEncryptor;
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

    public @NonNull EncryptedUserTokenView encrypt(@NonNull TextEncryptor textEncryptor) {
        return new EncryptedUserTokenView(
                platform,
                textEncryptor.encrypt(accessToken),
                textEncryptor.encrypt(refreshToken),
                expiringInstant,
                scopes,
                tokenType
        );
    }
}
