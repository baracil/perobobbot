package perobobbot.api.oauth;

import fpc.tools.lang.Secret;
import lombok.NonNull;
import perobobbot.api.data.Platform;
import perobobbot.api.data.view.Application;
import perobobbot.api.data.view.UserToken;

import java.net.URI;
import java.util.concurrent.CompletionStage;

/**
 * Basic OAuth operation for a specific platform
 */
public interface PlatformOAuth {

    /**
     * @return the platform the operations apply
     */
    @NonNull Platform platform();

    /**
     * @param state an opaque value used to identify the request and avoid CSRF
     * @param forceVerify if true the request for authorization will be asked even though the user is already identify on the browser
     * @return the URI than must be used to state the authorization code gran flow
     */
    @NonNull URI getAuthorizationCodeGrantFlowURI(@NonNull String clientId, @NonNull String state, boolean forceVerify);

    /**
     * Finalize an authorization code grant flow with the provided code
     * @param code the code to finalize the authorization code grant flow
     * @return a completion stage that completes with the requested {@link UserToken}
     */
    @NonNull CompletionStage<UserToken.Decrypted> finalizeAuthorizationCodeGrantFlow(@NonNull Application.Decrypted application, @NonNull String code);

    @NonNull UserToken.Decrypted refreshToken(@NonNull Application.Decrypted application, @NonNull Secret refreshToken);

}
