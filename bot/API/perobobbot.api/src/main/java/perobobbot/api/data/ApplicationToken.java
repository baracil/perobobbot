package perobobbot.api.data;

import fpc.tools.cipher.Decryptable;
import fpc.tools.cipher.Encryptable;
import fpc.tools.cipher.TextDecryptor;
import fpc.tools.cipher.TextEncryptor;
import fpc.tools.lang.Secret;
import io.micronaut.core.annotation.Introspected;

import java.time.Instant;

public sealed interface ApplicationToken<T> extends Decryptable<ApplicationToken.Decrypted>, Encryptable<ApplicationToken.Encrypted> permits ApplicationToken.Decrypted, ApplicationToken.Encrypted {

    Platform platform();

    T accessToken();

    Instant expirationInstant();


    @Introspected
    record Decrypted(Platform platform, Secret accessToken,
                     Instant expirationInstant) implements ApplicationToken<Secret>, AccessTokenProvider {

        public ApplicationToken.Encrypted encrypt(TextEncryptor textEncryptor) {
            return new Encrypted(platform, textEncryptor.encrypt(accessToken), expirationInstant);
        }

        @Override
        public ApplicationToken.Decrypted decrypt(TextDecryptor textDecryptor) {
            return this;
        }

        @Override
        public <T> T accept(Visitor<T> visitor) {
            return visitor.visit(this);
        }
    }

    @Introspected
    record Encrypted(Platform platform, String accessToken,
                     Instant expirationInstant) implements ApplicationToken<String> {
        public ApplicationToken.Decrypted decrypt(TextDecryptor textDecryptor) {
            return new Decrypted(platform, textDecryptor.decrypt(accessToken), expirationInstant);
        }

        @Override
        public ApplicationToken.Encrypted encrypt(TextEncryptor textEncryptor) {
            return this;
        }
    }
}
