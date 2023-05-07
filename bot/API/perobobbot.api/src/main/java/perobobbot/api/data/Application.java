package perobobbot.api.data;

import fpc.tools.cipher.Decryptable;
import fpc.tools.cipher.Encryptable;
import fpc.tools.cipher.TextDecryptor;
import fpc.tools.cipher.TextEncryptor;
import fpc.tools.lang.Secret;
import io.micronaut.core.annotation.Introspected;

public sealed interface Application<T>
        extends Decryptable<Application.Decrypted>, Encryptable<Application.Encrypted>
        permits Application.Decrypted, Application.Encrypted {

    Platform platform();

    String name();

    String clientId();

    T clientSecret();

    default SafeApplication toSafe() {
        return new SafeApplication(platform(),name(),clientId());
    }

    @Introspected
    record Encrypted(Platform platform,
                     String name,
                     String clientId,
                     String clientSecret) implements Application<String> {

        @Override
        public Application.Decrypted decrypt(TextDecryptor textDecryptor) {
            return new Decrypted(platform, name, clientId, textDecryptor.decrypt(clientSecret));
        }

        @Override
        public Application.Encrypted encrypt(TextEncryptor textEncryptor) {
            return this;
        }
    }

    @Introspected
    record Decrypted(Platform platform,
                     String name,
                     String clientId,
                     Secret clientSecret) implements Application<Secret> {

        @Override
        public Application.Encrypted encrypt(TextEncryptor textEncryptor) {
            return new Encrypted(platform, name, clientId, textEncryptor.encrypt(clientSecret));
        }

        @Override
        public Application.Decrypted decrypt(TextDecryptor textDecryptor) {
            return this;
        }
    }
}
