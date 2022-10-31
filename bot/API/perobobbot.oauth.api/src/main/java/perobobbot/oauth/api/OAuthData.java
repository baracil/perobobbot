package perobobbot.oauth.api;

import fpc.tools.lang.Secret;
import lombok.NonNull;
import perobobbot.api.data.Platform;


/**
 * Data use to get/refresh an access token
 */
public interface OAuthData {

    @NonNull Platform getPlatform();

    /**
     * @return the id of the client associated with the token
     */
    @NonNull String getClientId();

    /**
     * @param oAuthAccessMode the access mode (token and scope required to access the resource)
     * @return a secret containing the access token
     */
    @NonNull Secret getAccessToken(@NonNull OAuthAccessMode oAuthAccessMode);

    /**
     * Refresh the user or the application token (or both)
     * @param oAuthAccessMode the access mode (token and scope required to access the resource)
     */
    void refresh(@NonNull OAuthAccessMode oAuthAccessMode);

    @NonNull String getLogin();

    @NonNull String getUserId();
}
