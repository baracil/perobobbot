package perobobbot.api.data;

import fpc.tools.lang.Secret;
import lombok.NonNull;

public sealed interface AccessTokenProvider permits UserToken.Decrypted, ApplicationToken.Decrypted {

    @NonNull Secret accessToken();

    <T> @NonNull T accept(@NonNull Visitor<T> visitor);


    interface Visitor<T> {
        @NonNull T visit(@NonNull UserToken.Decrypted userToken);
        @NonNull T visit(@NonNull ApplicationToken.Decrypted appToken);
    }

}
