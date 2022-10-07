package perobobbot.api.data.view;

import fpc.tools.cipher.Decryptable;
import fpc.tools.cipher.Encryptable;
import fpc.tools.cipher.TextDecryptor;
import fpc.tools.cipher.TextEncryptor;
import fpc.tools.lang.Secret;
import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;
import perobobbot.api.data.Platform;

public sealed interface Application<T>
        extends Decryptable<Application.Decrypted>, Encryptable<Application.Encrypted>
        permits Application.Decrypted, Application.Encrypted {

    @NonNull Platform platform();

    @NonNull String name();

    @NonNull String clientId();

    @NonNull T clientSecret();

    default @NonNull SafeApplication toSafe() {
        return new SafeApplication(platform(),name(),clientId());
    }

    @Serdeable
    record Encrypted(@NonNull Platform platform,
                     @NonNull String name,
                     @NonNull String clientId,
                     @NonNull String clientSecret) implements Application<String> {

        @Override
        public @NonNull Application.Decrypted decrypt(@NonNull TextDecryptor textDecryptor) {
            return new Decrypted(platform, name, clientId, textDecryptor.decrypt(clientSecret));
        }

        @Override
        public @NonNull Application.Encrypted encrypt(@NonNull TextEncryptor textEncryptor) {
            return this;
        }
    }

    @Serdeable
    record Decrypted(@NonNull Platform platform,
                     @NonNull String name,
                     @NonNull String clientId,
                     @NonNull Secret clientSecret) implements Application<Secret> {

        @Override
        public @NonNull Application.Encrypted encrypt(@NonNull TextEncryptor textEncryptor) {
            return new Encrypted(platform, name, clientId, textEncryptor.encrypt(clientSecret));
        }

        @Override
        public @NonNull Application.Decrypted decrypt(@NonNull TextDecryptor textDecryptor) {
            return this;
        }
    }
}
