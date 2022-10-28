package perobobbot.oauth.api;

import fpc.tools.lang.Secret;
import lombok.NonNull;


public interface OAuthData {

    @NonNull String getClientId();

    @NonNull Secret getAccessToken(@NonNull OAuthAccessMode oAuthAccessMode);

    void refresh(@NonNull OAuthAccessMode oAuthAccessMode);

    @NonNull String getLogin();
}
