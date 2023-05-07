package perobobbot.api.data;

import fpc.tools.cipher.TextEncryptor;
import perobobbot.api.Identity;
import perobobbot.api.UserInfo;

public record TokenWithIdentity(UserToken.Decrypted token, UserInfo userInfo) implements Identity.Provider {
    public TokenWithIdentity {
        token.checkSameIdentity(userInfo);
    }

    @Override
    public Identity identity() {
        return userInfo.identity();
    }

    public UserToken.Encrypted getEncryptedToken(TextEncryptor textEncryptor) {
        return token.encrypt(textEncryptor);
    }
}
