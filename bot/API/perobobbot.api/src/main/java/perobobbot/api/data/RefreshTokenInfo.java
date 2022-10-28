package perobobbot.api.data;

import fpc.tools.cipher.Decryptable;
import fpc.tools.cipher.Encryptable;
import fpc.tools.cipher.TextDecryptor;
import fpc.tools.cipher.TextEncryptor;
import fpc.tools.lang.Secret;
import lombok.NonNull;

import java.time.Instant;

public sealed interface RefreshTokenInfo<T> extends Encryptable<RefreshTokenInfo.Encrypted>, Decryptable<RefreshTokenInfo.Decrypted> permits RefreshTokenInfo.Decrypted, RefreshTokenInfo.Encrypted {
    @NonNull Platform platform();
    @NonNull T refreshToken();
    @NonNull Instant expirationInstant();


    record Decrypted(@NonNull Platform platform,
                     @NonNull Secret refreshToken,
                     @NonNull Instant expirationInstant) implements RefreshTokenInfo<Secret> {
        @Override
        public @NonNull Decrypted decrypt(@NonNull TextDecryptor textDecryptor) {
            return this;
        }

        @Override
        public @NonNull Encrypted encrypt(@NonNull TextEncryptor textEncryptor) {
            return new Encrypted(platform, textEncryptor.encrypt(refreshToken), expirationInstant);
        }
    }

    record Encrypted(@NonNull Platform platform,
                     @NonNull String refreshToken,
                     @NonNull Instant expirationInstant) implements RefreshTokenInfo<String> {

        @Override
        public @NonNull Decrypted decrypt(@NonNull TextDecryptor textDecryptor) {
            return new Decrypted(platform, textDecryptor.decrypt(refreshToken),expirationInstant);
        }

        @Override
        public @NonNull Encrypted encrypt(@NonNull TextEncryptor textEncryptor) {
            return this;
        }
    }

}
