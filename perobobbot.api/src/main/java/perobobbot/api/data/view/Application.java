package perobobbot.api.data.view;

import fpc.tools.cipher.Encryptable;
import fpc.tools.cipher.TextEncryptor;
import fpc.tools.lang.Secret;
import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;
import perobobbot.api.data.Platform;

@Serdeable
public record Application(@NonNull Platform platform,
                          @NonNull String name,
                          @NonNull String clientId,
                          @NonNull Secret clientSecret) implements Encryptable<EncryptedApplication> {

    @Override
    public @NonNull EncryptedApplication encrypt(@NonNull TextEncryptor textEncryptor) {
        return new EncryptedApplication(platform, name, clientId, textEncryptor.encrypt(clientSecret));
    }

}
