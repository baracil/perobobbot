package perobobbot.launcher;

import fpc.tools.cipher.ProxyTextCipher;
import fpc.tools.cipher.TextCipher;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import lombok.NonNull;

@Singleton
@Named("Db")
public class DbTextCipher extends ProxyTextCipher {

    public DbTextCipher(@NonNull DataConfiguration dataConfiguration) {
        super(TextCipher.createAES(dataConfiguration.getDbPassPhrase()));
    }
}
