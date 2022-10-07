package perobobbot.twitch.oauth;

import fpc.tools.lang.Instants;
import fpc.tools.lang.Secret;
import io.micronaut.http.uri.UriBuilder;
import jakarta.inject.Singleton;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.api.UserInfo;
import perobobbot.api.data.Platform;
import perobobbot.api.data.view.Application;
import perobobbot.api.data.view.ApplicationToken;
import perobobbot.api.oauth.PlatformOAuth;
import perobobbot.api.oauth.Scope;
import perobobbot.api.oauth.TokenWithIdentity;
import perobobbot.twitch.api.Twitch;
import perobobbot.twitch.api.TwitchScope;
import perobobbot.twitch.oauth.dto.TwitchToken;

import java.net.URI;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor
@Singleton
public class TwitchOAuth implements PlatformOAuth {

    private final @NonNull TwitchOAuthClient twitchOAuthClient;
    private final @NonNull TwitchUserClient twitchUserClient;
    private final @NonNull Instants instants;
    private final String redirectUri = "https://femtoparsec.net/bot/oauth/callback/twitch";


    @Override
    public @NonNull Platform platform() {
        return Twitch.PLATFORM;
    }

    @Override
    public @NonNull URI getAuthorizationCodeGrantFlowURI(@NonNull String clientId, @NonNull String state, boolean forceVerify) {
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
    public @NonNull CompletionStage<TokenWithIdentity> finalizeAuthorizationCodeGrantFlow(@NonNull Application<Secret> application, @NonNull String code) {
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
    public @NonNull TokenWithIdentity refreshUserToken(@NonNull Application<Secret> application, @NonNull Secret refreshToken) {
        final var twitchToken = twitchOAuthClient.refreshToken(application.clientId(), application.clientSecret().value(), refreshToken.value());
        return create(application, twitchToken);
    }

    private @NonNull TokenWithIdentity create(@NonNull Application<Secret> application, @NonNull TwitchToken token) {
        final var userInfo = identify(application, token);
        return new TokenWithIdentity(token.toUserToken(userInfo.identification(), instants.now()), userInfo);
    }

    @Override
    public @NonNull ApplicationToken.Decrypted getAppToken(@NonNull Application<Secret> application) {
        final var appToken = twitchOAuthClient.getApplicationToken(application.clientId(), application.clientSecret().value());
        return appToken.toAppToken(instants.now());
    }

    @Override
    public void revoke(@NonNull String clientId, @NonNull Secret accessToken) {
        twitchOAuthClient.revokeToken(clientId, accessToken.value());
    }

    @Override
    public void validate(@NonNull Secret accessToken) {
        twitchOAuthClient.validate("Bearer " + accessToken);
    }

    private @NonNull UserInfo identify(@NonNull Application<?> application, @NonNull TwitchToken userToken) {
        final var authorization = "Bearer " + userToken.getAccessToken();
        final var value = twitchUserClient.getUsers(authorization, application.clientId());
        return value.getUsers()[0].toUserInfo();
    }

}
