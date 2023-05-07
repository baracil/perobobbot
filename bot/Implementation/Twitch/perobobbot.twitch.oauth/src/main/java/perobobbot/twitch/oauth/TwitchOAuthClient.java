package perobobbot.twitch.oauth;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;
import perobobbot.twitch.oauth.dto.TwitchToken;

@Client("https://id.twitch.tv/oauth2")
public interface TwitchOAuthClient {

    @Post("/token?grant_type=authorization_code")
    TwitchToken getTokenWithAuthorizationCode(
            @QueryValue("client_id") String clientId,
            @QueryValue("client_secret") String clientSecret,
            @QueryValue("code") String code,
            @QueryValue("redirect_uri") String redirectUri
    );

    @Post("/token?grant_type=refresh_token")
    TwitchToken refreshToken(
            @QueryValue("client_id") String clientId,
            @QueryValue("client_secret") String clientSecret,
            @QueryValue("refresh_token") String refreshToken
    );

    @Post("/revoke")
    void revokeToken(
            @QueryValue("client_id") String clientId,
            @QueryValue("token") String token
            );

    @Post("/token?grant_type=client_credentials")
    TwitchToken getApplicationToken(
            @QueryValue("client_id") String clientId,
            @QueryValue("client_secret") String clientSecret
    );

    @Get("https://id.twitch.tv/oauth2/validate")
    TwitchValidation validate(@Header String authorization);


}
