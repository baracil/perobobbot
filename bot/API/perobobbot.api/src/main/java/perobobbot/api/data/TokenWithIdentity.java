package perobobbot.api.data;

import fpc.tools.cipher.TextEncryptor;
import lombok.NonNull;
import perobobbot.api.Identity;
import perobobbot.api.UserInfo;

public record TokenWithIdentity(@NonNull UserToken.Decrypted token, @NonNull UserInfo userInfo) implements Identity.Provider {
    public TokenWithIdentity {
        token.checkSameIdentity(userInfo);
    }

    @Override
    public @NonNull Identity identity() {
        return userInfo.identity();
    }

    public @NonNull UserToken.Encrypted getEncryptedToken(@NonNull TextEncryptor textEncryptor) {
        return token.encrypt(textEncryptor);
    }
}
