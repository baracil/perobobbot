package perobobbot.data.launcher;

import fpc.tools.cipher.TextCipher;
import io.micronaut.context.annotation.Factory;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Factory
@RequiredArgsConstructor
public class TextCipherFactory {

    private final @NonNull @Inject DataConfiguration dataConfiguration;

    @Named("dbCipher")
    @Singleton
    public @NonNull TextCipher textCipher() {
        return TextCipher.createAES(dataConfiguration.getDbPassPhrase());
    }
}
