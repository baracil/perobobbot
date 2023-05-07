package perobobbot.oauth.api;

import fpc.tools.lang.Secret;
import perobobbot.api.data.Platform;


/**
 * Data use to get/refresh an access token
 */
public interface OAuthData {

    Platform getPlatform();

    /**
     * @return the id of the client associated with the token
     */
    String getClientId();

    /**
     * @param oAuthAccessMode the access mode (token and scope required to access the resource)
     * @return a secret containing the access token
     */
    Secret getAccessToken(OAuthAccessMode oAuthAccessMode);

    /**
     * Refresh the user or the application token (or both)
     * @param oAuthAccessMode the access mode (token and scope required to access the resource)
     */
    void refresh(OAuthAccessMode oAuthAccessMode);

    String getLogin();

    String getUserId();
}
