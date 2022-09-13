package perobobbot.data.io.view;

import fpc.tools.cipher.TextEncryptor;
import fpc.tools.lang.Secret;
import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;
import perobobbot.data.io.Platform;

@Serdeable
public record ApplicationView(@NonNull Platform platform,
                              @NonNull String name,
                              @NonNull String clientId,
                              @NonNull Secret clientSecret) {


    public @NonNull EncryptedApplicationView encrypt(@NonNull TextEncryptor encryptor) {
        return new EncryptedApplicationView(platform, name, clientId, encryptor.encrypt(clientSecret));
    }

}
