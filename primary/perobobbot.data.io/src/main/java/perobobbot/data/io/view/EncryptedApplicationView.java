package perobobbot.data.io.view;

import fpc.tools.cipher.TextDecryptor;
import fpc.tools.cipher.TextEncryptor;
import fpc.tools.lang.Secret;
import lombok.NonNull;
import perobobbot.data.io.Platform;

public record EncryptedApplicationView(@NonNull Platform platform,
                                       @NonNull String name,
                                       @NonNull String clientId,
                                       @NonNull String clientSecret) {


    public @NonNull ApplicationView decrypt(@NonNull TextDecryptor textDecryptor) {
        return new ApplicationView(platform,name,clientId,textDecryptor.decrypt(clientSecret));
    }

}
