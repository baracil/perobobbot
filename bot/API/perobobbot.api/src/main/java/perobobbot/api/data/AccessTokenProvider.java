package perobobbot.api.data;

import fpc.tools.lang.Secret;

public sealed interface AccessTokenProvider permits UserToken.Decrypted, ApplicationToken.Decrypted {

    Secret accessToken();

    <T> T accept(Visitor<T> visitor);


    interface Visitor<T> {
        T visit(UserToken.Decrypted userToken);
        T visit(ApplicationToken.Decrypted appToken);
    }

}
