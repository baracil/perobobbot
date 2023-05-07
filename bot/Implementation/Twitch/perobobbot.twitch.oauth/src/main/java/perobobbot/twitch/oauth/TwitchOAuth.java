package perobobbot.twitch.oauth;

import fpc.tools.lang.Instants;
import fpc.tools.lang.Secret;
import io.micronaut.http.uri.UriBuilder;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import perobobbot.api.Scope;
import perobobbot.api.UserInfo;
import perobobbot.api.data.Application;
import perobobbot.api.data.ApplicationToken;
import perobobbot.api.data.Platform;
import perobobbot.api.data.TokenWithIdentity;
import perobobbot.oauth.api.PlatformOAuth;
import perobobbot.twitch.api.Twitch;
import perobobbot.twitch.api.TwitchScope;
import perobobbot.twitch.oauth.dto.TwitchToken;

import java.net.URI;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor
@Singleton
public class TwitchOAuth implements PlatformOAuth {

    private final TwitchOAuthClient twitchOAuthClient;
    private final TwitchUserClient twitchUserClient;
    private final Instants instants;
    private final String redirectUri = "https://femtoparsec.net/bot/oauth/callback/twitch";


    @Override
    public Platform platform() {
        return Twitch.PLATFORM;
    }

    @Override
    public URI getAuthorizationCodeGrantFlowURI(String clientId, String state, boolean forceVerify) {
        return UriBuilder.of(URI.create("https://id.twitch.tv/oauth2/authorize"))
                         .queryParam("client_id", clientId)
                         .queryParam("force_verify", forceVerify)
                         .queryParam("redirect_uri", redirectUri)
                         .queryParam("response_type", "code")
                         .queryParam("scope", Scope.joinScopeNames(TwitchScope.valuesAsSet(), ' '))
                         .queryParam("state", state)
                         .build();
    }

    @Override
    public CompletionStage<TokenWithIdentity> finalizeAuthorizationCodeGrantFlow(Application<Secret> application, String code) {
        return CompletableFuture.supplyAsync(() -> {
            final var twitchToken = twitchOAuthClient.getTokenWithAuthorizationCode(
                    application.clientId(),
                    application.clientSecret().value(),
                    code,
                    redirectUri);

            return create(application, twitchToken);
        });
    }

    @Override
    public TokenWithIdentity refreshUserToken(Application<Secret> application, Secret refreshToken) {
        final var twitchToken = twitchOAuthClient.refreshToken(application.clientId(), application.clientSecret().value(), refreshToken.value());
        return create(application, twitchToken);
    }

    private TokenWithIdentity create(Application<Secret> application, TwitchToken token) {
        final var userInfo = identify(application, token);
        return new TokenWithIdentity(token.toUserToken(userInfo.identity(), instants.now()), userInfo);
    }

    @Override
    public ApplicationToken.Decrypted getAppToken(Application<Secret> application) {
        final var appToken = twitchOAuthClient.getApplicationToken(application.clientId(), application.clientSecret().value());
        return appToken.toAppToken(instants.now());
    }

    @Override
    public void revoke(String clientId, Secret accessToken) {
        twitchOAuthClient.revokeToken(clientId, accessToken.value());
    }

    @Override
    public void validate(Secret accessToken) {
        twitchOAuthClient.validate("Bearer " + accessToken);
    }

    private UserInfo identify(Application<?> application, TwitchToken userToken) {
        final var authorization = "Bearer " + userToken.getAccessToken();
        final var value = twitchUserClient.getUsers(authorization, application.clientId());
        return value.getUsers()[0].toUserInfo();
    }

}
