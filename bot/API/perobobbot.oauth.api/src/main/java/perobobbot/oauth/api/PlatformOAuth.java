package perobobbot.oauth.api;

import fpc.tools.lang.Secret;
import perobobbot.api.data.*;

import java.net.URI;
import java.util.concurrent.CompletionStage;

/**
 * Basic OAuth operation for a specific platform
 */
public interface PlatformOAuth {

    /**
     * @return the platform the operations apply
     */
    Platform platform();

    /**
     * @param state an opaque value used to identify the request and avoid CSRF
     * @param forceVerify if true the request for authorization will be asked even though the user is already identify on the browser
     * @return the URI than must be used to state the authorization code gran flow
     */
    URI getAuthorizationCodeGrantFlowURI(String clientId, String state, boolean forceVerify);

    /**
     * Finalize an authorization code grant flow with the provided code
     * @param code the code to finalize the authorization code grant flow
     * @return a completion stage that completes with the requested {@link UserToken}
     */
    CompletionStage<TokenWithIdentity> finalizeAuthorizationCodeGrantFlow(Application<Secret> application, String code);

    TokenWithIdentity refreshUserToken(Application<Secret> application, Secret refreshToken);

    ApplicationToken.Decrypted getAppToken(Application<Secret> application);

    void revoke(String clientId, Secret accessToken);

    void validate(Secret accessToken);
}
