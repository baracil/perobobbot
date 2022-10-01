package net.femtoparsec.perobobbot.oauth;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import fpc.tools.lang.Futures;
import fpc.tools.lang.Secret;
import fpc.tools.lang.StringTool;
import fpc.tools.lang.Subscription;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import perobobbot.api.data.Platform;
import perobobbot.api.data.view.ApplicationToken;
import perobobbot.api.data.view.UserToken;
import perobobbot.api.oauth.PlatformOAuth;
import perobobbot.oauth.AuthorizationCodeGranFlow;
import perobobbot.oauth.Failure;
import perobobbot.oauth.FlowFailure;
import perobobbot.oauth.OAuthManager;
import perobobbot.service.api.ApplicationService;

@RequiredArgsConstructor
@Slf4j
public class DefaultOAuthManager implements OAuthManager {


    private final @NonNull ApplicationService applicationService;
    private final @NonNull PlatformOAuths platforms;
    private final @NonNull RendezvousMaker rendezVousMaker = new RendezvousMaker();

    public DefaultOAuthManager(@NonNull ApplicationService applicationService, @NonNull ImmutableList<PlatformOAuth> platformOAuths) {
        this.applicationService = applicationService;
        this.platforms = new PlatformOAuths(platformOAuths);
    }

    @Override
    public @NonNull AuthorizationCodeGranFlow startAuthorizationCodeGrantFlow(@NonNull Platform platform) {
        final var platformOAuth = platforms.get(platform);
        final var clientId = applicationService.getApplicationClientId(platform);
        final var state = StringTool.random(64);
        final var uri = platformOAuth.getAuthorizationCodeGrantFlowURI(clientId, state, true);
        final var flow = new DefaultAuthorizationCodeGranFlow(uri, state, this);
        rendezVousMaker.addFlow(state, flow);
        return flow;
    }

    @NonNull
    @Override
    public ApplicationToken.Decrypted getAppToken(@NonNull Platform platform) {
        final var application = applicationService.getApplication(platform);
        final var platformOAuth = platforms.get(platform);
        return platformOAuth.getAppToken(application);
    }

    @Override
    public @NonNull UserToken.Decrypted refreshUserToken(@NonNull Platform platform, @NonNull Secret refreshToken) {
        final var application = applicationService.getApplication(platform);
        final var platformOAuth = platforms.get(platform);
        return platformOAuth.refreshUserToken(application, refreshToken);
    }

    public void failFlow(@NonNull String state, @NonNull Failure failure) {
        rendezVousMaker.extractFlow(state).ifPresent(future -> future.completeExceptionally(new FlowFailure(failure)));
    }

    @Override
    public @NonNull ImmutableSet<Platform> getManagedPlatforms() {
        return platforms.getPlatforms();
    }

    @Override
    public @NonNull Subscription register(@NonNull PlatformOAuth platformOAuth) {
        return platforms.add(platformOAuth);
    }

    @Override
    public void revokeToken(@NonNull UserToken<Secret> userToken) {
        final var application = applicationService.getApplication(userToken.platform());
        platforms.get(userToken.platform())
                .revoke(application.clientId(),userToken.accessToken());
    }

    @Override
    public void revokeToken(@NonNull ApplicationToken<Secret> appToken) {
        final var application = applicationService.getApplication(appToken.platform());
        platforms.get(appToken.platform())
                 .revoke(application.clientId(),appToken.accessToken());
    }

    @Override
    public void handleCallback(@NonNull Platform platform, @NonNull ImmutableMap<String, String> queryValues) {
        LOG.info("Receive callback from '" + platform + "'  error='" + queryValues.getOrDefault("error", "none") + "'");

        final var info = CallbackInfo.parse(queryValues).orElse(null);
        final var platformOAuth = platforms.find(platform).orElse(null);
        if (platformOAuth == null) {
            LOG.warn("No platformOAuth for '" + platform + "'");
            return;
        }

        if (info == null) {
            return;
        }
        final var future = rendezVousMaker.extractFlow(info.state()).orElse(null);
        if (future == null) {
            LOG.warn("No flow associated with the state '" + info.state() + "'");
            return;
        }

        try {
            if (info instanceof CallbackInfo.Success success) {
                final var application = applicationService.getApplication(platform);
                System.out.println(">>> " + application);
                Futures.join(platformOAuth.finalizeAuthorizationCodeGrantFlow(application, success.code()), future);
            } else if (info instanceof CallbackInfo.Error error) {
                future.completeExceptionally(new FlowFailure(new Failure.Error(error.description())));
            }
        } catch (Throwable t) {
            t.printStackTrace();
            future.completeExceptionally(t);
        }

    }

}
