package perobobbot.data.io.view;

import fpc.tools.cipher.TextDecryptor;
import lombok.NonNull;

import java.time.Instant;
import java.util.List;

public record EncryptedUserTokenView(@NonNull String platform,
                                     @NonNull String accessToken,
                                     @NonNull String refreshToken,
                                     @NonNull Instant expiringInstant,
                                     @NonNull List<String> scopes,
                                     @NonNull String tokenType) {

    @NonNull UserTokenView decrypt(@NonNull TextDecryptor textDecryptor) {
        return new UserTokenView(
                platform,
                textDecryptor.decrypt(accessToken),
                textDecryptor.decrypt(refreshToken),
                expiringInstant,
                scopes,
                tokenType
        );
    }

}
