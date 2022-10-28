package perobobbot.web.controller;

import com.google.common.collect.ImmutableMap;
import io.micronaut.core.util.CollectionUtils;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.views.View;
import lombok.NonNull;
import perobobbot.api.data.Platform;
import perobobbot.api.data.TokenWithIdentity;
import perobobbot.oauth.api.Failure;
import perobobbot.oauth.api.OAuthManager;
import perobobbot.service.api.UserTokenService;
import perobobbot.web.api.AuthenticationApi;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.Set;

@Controller(AuthenticationApi.PATH)
public class AuthenticationController implements AuthenticationApi {

    private final @NonNull OAuthManager oAuthManager;
    private final @NonNull UserTokenService userTokenService;

    public AuthenticationController(@NonNull OAuthManager oAuthManager, @NonNull UserTokenService userTokenService) {
        this.oAuthManager = oAuthManager;
        this.userTokenService = userTokenService;
    }


    @Override
    public @NonNull Set<Platform> listManagedPlatforms() {
        return oAuthManager.getManagedPlatforms();
    }


    @Override
    public @NonNull URI startAuthorizationCodeGrantFlow(@NonNull @PathVariable Platform platform) {
        final var flow =  oAuthManager.startAuthorizationCodeGrantFlow(platform);
        flow.whenComplete(this::onUserToken, this::onFailure);

        return flow.getUri();
    }

    @Override
    public void launchAuthorizationCodeGrantFlow(@NonNull @PathVariable Platform platform) throws IOException {
        final var flow = oAuthManager.startAuthorizationCodeGrantFlow(platform);
        flow.whenComplete(this::onUserToken, this::onFailure);

        try {
            Desktop.getDesktop().browse(flow.getUri());
        } catch (IOException e) {
            flow.setFailed(new Failure.Error(e.getMessage()));
            throw e;
        }
    }

    @View("authentication")
    @Override
    public @NonNull HttpResponse<?> authenticate(@NonNull @PathVariable  Platform platform) {
        final var flow = oAuthManager.startAuthorizationCodeGrantFlow(platform);
        flow.whenComplete(this::onUserToken, this::onFailure);

        return HttpResponse.ok(CollectionUtils.mapOf("callbackUrl", flow.getUri().toString()));
    }


    @Get("/callback/{platform}{?values*}")
    public void getCallback(@NonNull @PathVariable Platform platform,
                            @NonNull @QueryValue("values") Map<String, String> values) {
        oAuthManager.handleCallback(platform, ImmutableMap.copyOf(values));
    }


    private void onUserToken(@NonNull TokenWithIdentity tokenWithIdentity) {
        try {
            userTokenService.setUserToken(tokenWithIdentity);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private void onFailure(@NonNull Throwable throwable) {
        throwable.printStackTrace();
    }

}
