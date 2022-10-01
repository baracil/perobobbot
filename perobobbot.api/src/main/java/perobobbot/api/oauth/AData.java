package perobobbot.api.oauth;

import fpc.tools.lang.Secret;
import lombok.NonNull;
import perobobbot.api.data.Platform;
import perobobbot.api.data.view.UserIdentity;


public interface AData {

    @NonNull UserIdentity getUserIdentity();

    @NonNull String getClientId();

    default @NonNull Platform getPlatform() {
        return getUserIdentity().platform();
    }

    @NonNull Secret getAccessToken(@NonNull OAuthAccessMode oAuthAccessMode);

    void refresh(@NonNull OAuthAccessMode oAuthAccessMode);

}
