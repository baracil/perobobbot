package perobobbot.api.data.view;

import fpc.tools.cipher.Decryptable;
import fpc.tools.cipher.TextDecryptor;
import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;
import perobobbot.api.data.Platform;

@Serdeable
public record EncryptedApplication(@NonNull Platform platform,
                                   @NonNull String name,
                                   @NonNull String clientId,
                                   @NonNull String clientSecret) implements Decryptable<Application> {

    @Override
    public @NonNull Application decrypt(@NonNull TextDecryptor textDecryptor) {
        return new Application(platform,name,clientId,textDecryptor.decrypt(clientSecret));
    }

}
