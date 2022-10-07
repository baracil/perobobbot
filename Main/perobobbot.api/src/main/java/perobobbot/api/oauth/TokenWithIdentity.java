package perobobbot.api.oauth;

import fpc.tools.cipher.TextEncryptor;
import lombok.NonNull;
import perobobbot.api.Identification;
import perobobbot.api.UserInfo;
import perobobbot.api.data.view.UserToken;

public record TokenWithIdentity(@NonNull UserToken.Decrypted token, @NonNull UserInfo userInfo) implements Identification.Provider {
    public TokenWithIdentity {
        token.checkSamePerson(userInfo);
    }

    @Override
    public @NonNull Identification identification() {
        return userInfo.identification();
    }

    public @NonNull UserToken.Encrypted getEncryptedToken(@NonNull TextEncryptor textEncryptor) {
        return token.encrypt(textEncryptor);
    }
}
