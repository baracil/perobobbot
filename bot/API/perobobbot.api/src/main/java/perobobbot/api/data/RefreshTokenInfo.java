package perobobbot.api.data;

import fpc.tools.cipher.Decryptable;
import fpc.tools.cipher.Encryptable;
import fpc.tools.cipher.TextDecryptor;
import fpc.tools.cipher.TextEncryptor;
import fpc.tools.lang.Secret;

import java.time.Instant;

public sealed interface RefreshTokenInfo<T> extends Encryptable<RefreshTokenInfo.Encrypted>, Decryptable<RefreshTokenInfo.Decrypted> permits RefreshTokenInfo.Decrypted, RefreshTokenInfo.Encrypted {
    Platform platform();
    T refreshToken();
    Instant expirationInstant();


    record Decrypted(Platform platform,
                     Secret refreshToken,
                     Instant expirationInstant) implements RefreshTokenInfo<Secret> {
        @Override
        public Decrypted decrypt(TextDecryptor textDecryptor) {
            return this;
        }

        @Override
        public Encrypted encrypt(TextEncryptor textEncryptor) {
            return new Encrypted(platform, textEncryptor.encrypt(refreshToken), expirationInstant);
        }
    }

    record Encrypted(Platform platform,
                     String refreshToken,
                     Instant expirationInstant) implements RefreshTokenInfo<String> {

        @Override
        public Decrypted decrypt(TextDecryptor textDecryptor) {
            return new Decrypted(platform, textDecryptor.decrypt(refreshToken),expirationInstant);
        }

        @Override
        public Encrypted encrypt(TextEncryptor textEncryptor) {
            return this;
        }
    }

}
