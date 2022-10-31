package perobobbot.oauth.api;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import fpc.tools.lang.Secret;
import lombok.NonNull;
import perobobbot.api.data.ApplicationToken;
import perobobbot.api.data.Platform;
import perobobbot.api.data.TokenWithIdentity;
import perobobbot.api.data.UserToken;

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
    @NonNull AuthorizationCodeGranFlow startAuthorizationCodeGrantFlow(@NonNull Platform platform);


    @NonNull TokenWithIdentity refreshUserToken(@NonNull Platform platform, @NonNull Secret refreshToken);

    @NonNull ApplicationToken.Decrypted getAppToken(@NonNull Platform platform);

    /**
     * Handle the callback from the platform
     *
     * @param platform    the platform that called
     * @param queryValues the query values associated with the callback
     */
    void handleCallback(@NonNull Platform platform, @NonNull ImmutableMap<String, String> queryValues);

    /**
     * Call to fail the flow (for instance because the authorization code flow could not be started like an invalid provided URI)
     *
     * @param state   the state associated with the flow
     * @param failure the failure
     */
    void failFlow(@NonNull String state, @NonNull Failure failure);

    @NonNull ImmutableSet<Platform> getManagedPlatforms();

    void revokeToken(@NonNull UserToken<Secret> userToken);

    void revokeToken(@NonNull ApplicationToken<Secret> userToken);

    void validateToken(@NonNull UserToken<Secret> userToken);


}
