package perobobbot.oauth;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import fpc.tools.lang.Secret;
import fpc.tools.lang.Subscription;
import lombok.NonNull;
import net.femtoparsec.perobobbot.oauth.DefaultOAuthManager;
import perobobbot.api.data.Platform;
import perobobbot.api.data.view.UserToken;
import perobobbot.api.oauth.PlatformOAuth;
import perobobbot.service.api.ApplicationService;

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


    @NonNull UserToken.Decrypted refresh(@NonNull Platform platform, @NonNull Secret refreshToken);

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

    @NonNull Subscription register(@NonNull PlatformOAuth platformOAuth);

    static @NonNull OAuthManager create(@NonNull ApplicationService applicationService, @NonNull ImmutableList<PlatformOAuth> platformOAuths) {
        return new DefaultOAuthManager(applicationService,platformOAuths);
    }

    @NonNull ImmutableSet<Platform> getManagedPlatforms();

}
