package perobobbot.oauth.api;

import fpc.tools.lang.Secret;
import perobobbot.api.data.ApplicationToken;
import perobobbot.api.data.Platform;
import perobobbot.api.data.TokenWithIdentity;
import perobobbot.api.data.UserToken;

import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Manager authorization flow
 */
public interface OAuthManager {

    int VERSION = 1;

    /**
     * Start an authorization code grant flow
     *
     * @param platform the platform on which the authorization must be done
     * @return the flow to perform the authorization
     */
    AuthorizationCodeGranFlow startAuthorizationCodeGrantFlow(Platform platform, Consumer<TokenWithIdentity> onResult, Consumer<Throwable> onError);


    TokenWithIdentity refreshUserToken(Platform platform, Secret refreshToken);

    ApplicationToken.Decrypted getAppToken(Platform platform);

    /**
     * Handle the callback from the platform
     *
     * @param platform    the platform that called
     * @param queryValues the query values associated with the callback
     */
    void handleCallback(Platform platform, Map<String, String> queryValues);

    /**
     * Call to fail the flow (for instance because the authorization code flow could not be started like an invalid provided URI)
     *
     * @param state   the state associated with the flow
     * @param failure the failure
     */
    void failFlow(String state, Failure failure);

    Set<Platform> getManagedPlatforms();

    void revokeToken(UserToken<Secret> userToken);

    void revokeToken(ApplicationToken<Secret> userToken);

    void validateToken(UserToken<Secret> userToken);


}
