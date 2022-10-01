package perobobbot.api.oauth;

import fpc.tools.lang.Secret;
import lombok.NonNull;
import perobobbot.api.data.Platform;
import perobobbot.api.data.view.UserIdentity;


public interface OAuthData {

    @NonNull UserIdentity getUserIdentity();

    @NonNull String getClientId();

    @NonNull Secret getAccessToken(@NonNull OAuthAccessMode oAuthAccessMode);

    void refresh(@NonNull OAuthAccessMode oAuthAccessMode);


    default @NonNull Platform getPlatform() {
        return getUserIdentity().platform();
    }

    default @NonNull String getLogin() {
        return getUserIdentity().login();
    }

}
