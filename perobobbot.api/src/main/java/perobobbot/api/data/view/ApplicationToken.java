package perobobbot.api.data.view;

import fpc.tools.cipher.Decryptable;
import fpc.tools.cipher.Encryptable;
import fpc.tools.cipher.TextDecryptor;
import fpc.tools.cipher.TextEncryptor;
import fpc.tools.lang.Secret;
import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;
import perobobbot.api.data.Platform;

import java.time.Instant;

public sealed interface ApplicationToken<T> extends Decryptable<ApplicationToken.Decrypted>, Encryptable<ApplicationToken.Encrypted> permits ApplicationToken.Decrypted, ApplicationToken.Encrypted {

    @NonNull Platform platform();

    @NonNull T accessToken();

    @NonNull Instant expirationInstant();


    @Serdeable
    record Decrypted(@NonNull Platform platform, @NonNull Secret accessToken,
                     @NonNull Instant expirationInstant) implements ApplicationToken<Secret>, AccessTokenProvider {

        public @NonNull ApplicationToken.Encrypted encrypt(@NonNull TextEncryptor textEncryptor) {
            return new Encrypted(platform, textEncryptor.encrypt(accessToken), expirationInstant);
        }

        @Override
        public @NonNull ApplicationToken.Decrypted decrypt(@NonNull TextDecryptor textDecryptor) {
            return this;
        }

        @Override
        public <T> @NonNull T accept(@NonNull Visitor<T> visitor) {
            return visitor.visit(this);
        }
    }

    @Serdeable
    record Encrypted(@NonNull Platform platform, @NonNull String accessToken,
                     @NonNull Instant expirationInstant) implements ApplicationToken<String> {
        public @NonNull ApplicationToken.Decrypted decrypt(@NonNull TextDecryptor textDecryptor) {
            return new Decrypted(platform, textDecryptor.decrypt(accessToken), expirationInstant);
        }

        @Override
        public @NonNull ApplicationToken.Encrypted encrypt(@NonNull TextEncryptor textEncryptor) {
            return this;
        }
    }
}
