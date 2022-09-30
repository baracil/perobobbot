package perobobbot.api.oauth;

import fpc.tools.lang.Secret;
import lombok.NonNull;
import perobobbot.api.data.Platform;
import perobobbot.api.data.view.Application;
import perobobbot.api.data.view.UserToken;

public record TokenData(
        @NonNull UserToken.Decrypted token,
        @NonNull Application.Decrypted application) {

    public @NonNull Platform platform() {
        return token.platform();
    }

    public @NonNull Secret refreshToken() {
        return token.refreshToken();
    }

    public @NonNull TokenData withNewToken(@NonNull UserToken.Decrypted userToken) {
        return new TokenData(userToken,application);
    }

    public @NonNull Secret accessToken() {
        return token.accessToken();
    }
}
