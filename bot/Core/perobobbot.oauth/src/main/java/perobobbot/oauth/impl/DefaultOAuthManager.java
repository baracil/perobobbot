package perobobbot.oauth.impl;

import fpc.tools.lang.Secret;
import fpc.tools.lang.StringTool;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import perobobbot.api.data.ApplicationToken;
import perobobbot.api.data.Platform;
import perobobbot.api.data.TokenWithIdentity;
import perobobbot.api.data.UserToken;
import perobobbot.oauth.api.AuthorizationCodeGranFlow;
import perobobbot.oauth.api.Failure;
import perobobbot.oauth.api.OAuthManager;
import perobobbot.oauth.api.PlatformOAuth;
import perobobbot.service.api.ApplicationService;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletionStage;
import java.util.function.Consumer;

@RequiredArgsConstructor
@Slf4j
public class DefaultOAuthManager implements OAuthManager {


    private final @NonNull ApplicationService applicationService;
    private final @NonNull PlatformOAuths platforms;
    private final @NonNull RendezvousMaker rendezVousMaker = new RendezvousMaker();

    public DefaultOAuthManager(@NonNull ApplicationService applicationService, @NonNull List<PlatformOAuth> platformOAuths) {
        this.applicationService = applicationService;
        this.platforms = new PlatformOAuths(platformOAuths);
    }

    @Override
    public @NonNull AuthorizationCodeGranFlow startAuthorizationCodeGrantFlow(@NonNull Platform platform, @NonNull Consumer<TokenWithIdentity> onResult, @NonNull Consumer<Throwable> onError) {
        final var platformOAuth = platforms.get(platform);
        final var clientId = applicationService.getApplicationClientId(platform);
        final var state = StringTool.random(64);
        final var uri = platformOAuth.getAuthorizationCodeGrantFlowURI(clientId, state, true);
        final var flow = new DefaultAuthorizationCodeGranFlow(uri, state, onResult, onError);
        rendezVousMaker.addFlow(flow);
        return flow;
    }

    @Override
    public void validateToken(@NonNull UserToken<Secret> userToken) {
        final var platformOAuth = platforms.get(userToken.platform());
        platformOAuth.validate(userToken.accessToken());
    }

    @NonNull
    @Override
    public ApplicationToken.Decrypted getAppToken(@NonNull Platform platform) {
        final var application = applicationService.getApplication(platform);
        final var platformOAuth = platforms.get(platform);
        final var token = platformOAuth.getAppToken(application);
        return applicationService.saveApplicationToken(token);
    }

    @Override
    public @NonNull TokenWithIdentity refreshUserToken(@NonNull Platform platform, @NonNull Secret refreshToken) {
        final var application = applicationService.getApplication(platform);
        final var platformOAuth = platforms.get(platform);
        return platformOAuth.refreshUserToken(application, refreshToken);
    }

    @Override
    public @NonNull Set<Platform> getManagedPlatforms() {
        return platforms.getPlatforms();
    }

    @Override
    public void revokeToken(@NonNull UserToken<Secret> userToken) {
        final var application = applicationService.getApplication(userToken.platform());
        platforms.get(userToken.platform())
                 .revoke(application.clientId(), userToken.accessToken());
    }

    @Override
    public void revokeToken(@NonNull ApplicationToken<Secret> appToken) {
        final var application = applicationService.getApplication(appToken.platform());
        platforms.get(appToken.platform())
                 .revoke(application.clientId(), appToken.accessToken());
    }

    @Override
    public void failFlow(@NonNull String state, @NonNull Failure failure) {
        rendezVousMaker.extractFlow(state).ifPresent(flow -> flow.completeWithError(failure));
    }

    @Override
    public void handleCallback(@NonNull Platform platform, @NonNull Map<String, String> queryValues) {
        LOG.info("Receive callback from '" + platform.name() + "'  error='" + queryValues.getOrDefault("error", "none") + "'");

        final var info = CallbackInfo.parse(queryValues).orElse(null);
        final var platformOAuth = platforms.find(platform).orElse(null);
        if (platformOAuth == null) {
            LOG.warn("No platformOAuth for '" + platform + "'");
            return;
        }

        if (info == null) {
            return;
        }
        final var flow = rendezVousMaker.extractFlow(info.state()).orElse(null);
        if (flow == null) {
            LOG.warn("No flow associated with the state '" + info.state() + "'");
            return;
        }

        try {
            if (info instanceof CallbackInfo.Success success) {
                final var application = applicationService.getApplication(platform);
                join(platformOAuth.finalizeAuthorizationCodeGrantFlow(application, success.code()), flow);
            } else if (info instanceof CallbackInfo.Error error) {
                flow.completeWithError(new Failure.Error(error.description()));
            }
        } catch (Throwable t) {
            LOG.warn("Error while handling callback", t);
            flow.completeWithError(new Failure.Error(t.getMessage()));
        }

    }

    private void join(@NonNull CompletionStage<TokenWithIdentity> future, @NonNull AuthorizationCodeGranFlow flow) {
        future.whenComplete((token, error) -> {
            if (error != null) {
                flow.completeWithError(new Failure.Error(error.getMessage()));
            } else {
                flow.completWithSuccess(token);
            }
        });
    }

}
