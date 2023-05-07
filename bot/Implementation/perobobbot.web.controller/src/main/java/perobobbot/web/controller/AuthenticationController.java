package perobobbot.web.controller;

import io.micronaut.core.util.CollectionUtils;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.views.View;
import lombok.RequiredArgsConstructor;
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
@ExecuteOn(TaskExecutors.IO)
@RequiredArgsConstructor
public class AuthenticationController implements AuthenticationApi {

    private final OAuthManager oAuthManager;
    private final UserTokenService userTokenService;

    @Override
    public Set<Platform> listManagedPlatforms() {
        return oAuthManager.getManagedPlatforms();
    }


    @Override
    public URI startAuthorizationCodeGrantFlow(@PathVariable Platform platform) {
        final var flow =  oAuthManager.startAuthorizationCodeGrantFlow(platform,this::onUserToken,this::onFailure);
        return flow.getUri();
    }

    @Override
    public void launchAuthorizationCodeGrantFlow(@PathVariable Platform platform) throws IOException {
        final var flow = oAuthManager.startAuthorizationCodeGrantFlow(platform,this::onUserToken,this::onFailure);

        try {
            Desktop.getDesktop().browse(flow.getUri());
        } catch (IOException e) {
            oAuthManager.failFlow(flow.getState(), new Failure.Error(e.getMessage()));
            throw e;
        }
    }

    @View("authentication")
    @Override
    public HttpResponse<?> authenticate(@PathVariable  Platform platform) {
        final var flow = oAuthManager.startAuthorizationCodeGrantFlow(platform, this::onUserToken, this::onFailure);

        return HttpResponse.ok(CollectionUtils.mapOf("callbackUrl", flow.getUri().toString()));
    }


    @Get("/callback/{platform}{?values*}")
    public void getCallback(@PathVariable Platform platform,
                            @QueryValue("values") Map<String, String> values) {
        oAuthManager.handleCallback(platform, values);
    }


    private void onUserToken(TokenWithIdentity tokenWithIdentity) {
        try {
            userTokenService.setUserToken(tokenWithIdentity);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private void onFailure(Throwable throwable) {
        throwable.printStackTrace();
    }

}
