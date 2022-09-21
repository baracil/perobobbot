package perobobbot.oauth;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import net.femtoparsec.perobobbot.oauth.DefaultOAuthManager;
import perobobbot.api.data.Platform;
import perobobbot.api.oauth.PlatformOAuth;
import perobobbot.service.api.ApplicationService;

/**
 * Manager authorization flow
 */
public interface OAuthManager {

    /**
     * Start an authorization code grant flow
     *
     * @param platform the platform on which the authorization must be done
     * @return the flow to perform the authorization
     */
    @NonNull AuthorizationCodeGranFlow startAuthorizationCodeGrantFlow(@NonNull Platform platform);

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

    static @NonNull OAuthManager create(@NonNull ImmutableList<PlatformOAuth> platformOAuths, @NonNull ApplicationService applicationService) {
        return DefaultOAuthManager.create(applicationService, platformOAuths);
    }
}
