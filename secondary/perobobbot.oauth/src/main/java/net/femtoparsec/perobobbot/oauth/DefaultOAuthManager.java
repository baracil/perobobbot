package net.femtoparsec.perobobbot.oauth;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import fpc.tools.lang.Futures;
import fpc.tools.lang.MapTool;
import fpc.tools.lang.StringTool;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import perobobbot.api.data.Platform;
import perobobbot.api.data.UnmanagedPlatform;
import perobobbot.api.oauth.PlatformOAuth;
import perobobbot.oauth.AuthorizationCodeGranFlow;
import perobobbot.oauth.Failure;
import perobobbot.oauth.FlowFailure;
import perobobbot.oauth.OAuthManager;
import perobobbot.service.api.ApplicationService;

@RequiredArgsConstructor
@Slf4j
public class DefaultOAuthManager implements OAuthManager {


    public static @NonNull DefaultOAuthManager create(
            @NonNull ApplicationService applicationService,
            @NonNull ImmutableList<PlatformOAuth> platformOAuths) {
        return new DefaultOAuthManager(
                applicationService,
                platformOAuths.stream().collect(MapTool.collector(PlatformOAuth::platform))
        );
    }

    private final @NonNull ApplicationService applicationService;
    private final @NonNull ImmutableMap<Platform, PlatformOAuth> platforms;
    private final @NonNull RendezvousMaker rendezVousMaker = new RendezvousMaker();

    @Override
    public @NonNull AuthorizationCodeGranFlow startAuthorizationCodeGrantFlow(@NonNull Platform platform) {
        final var clientId = applicationService.getApplicationClientId(platform);
        final var platformOAuth = getPlatformOAuth(platform);
        final var state = StringTool.random(64);
        final var uri = platformOAuth.getAuthorizationCodeGrantFlowURI(clientId, state, true);
        final var flow = new DefaultAuthorizationCodeGranFlow(uri, state, this);
        rendezVousMaker.addFlow(state, flow);
        return flow;
    }


    public void failFlow(@NonNull String state, @NonNull Failure failure) {
        rendezVousMaker.extractFlow(state).ifPresent(future -> future.completeExceptionally(new FlowFailure(failure)));
    }

    @Override
    public void handleCallback(@NonNull Platform platform, @NonNull ImmutableMap<String, String> queryValues) {
        LOG.info("Receive callback from '" + platform + "'  error='" + queryValues.getOrDefault("error", "none") + "'");

        final var info = CallbackInfo.parse(queryValues).orElse(null);
        final var platformOAuth = platforms.get(platform);
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

        if (info instanceof CallbackInfo.Success success) {
            final var application = applicationService.getApplication(platform);
            Futures.join(platformOAuth.finalizeAuthorizationCodeGrantFlow(application, success.code()), future);
        } else if (info instanceof CallbackInfo.Error error) {
            future.completeExceptionally(new FlowFailure(new Failure.Error(error.description())));
        }

    }


    private @NonNull PlatformOAuth getPlatformOAuth(@NonNull Platform platform) {
        final var platformOAuth = platforms.get(platform);
        if (platformOAuth == null) {
            throw new UnmanagedPlatform(platform);
        }
        return platformOAuth;
    }


}
