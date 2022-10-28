package perobobbot.twitch.oauth;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;
import lombok.NonNull;
import perobobbot.twitch.oauth.dto.TwitchToken;

@Client("https://id.twitch.tv/oauth2")
public interface TwitchOAuthClient {

    @Post("/token?grant_type=authorization_code")
    @NonNull TwitchToken getTokenWithAuthorizationCode(
            @NonNull @QueryValue("client_id") String clientId,
            @NonNull @QueryValue("client_secret") String clientSecret,
            @NonNull @QueryValue("code") String code,
            @NonNull @QueryValue("redirect_uri") String redirectUri
    );

    @Post("/token?grant_type=refresh_token")
    @NonNull TwitchToken refreshToken(
            @NonNull @QueryValue("client_id") String clientId,
            @NonNull @QueryValue("client_secret") String clientSecret,
            @NonNull @QueryValue("refresh_token") String refreshToken
    );

    @Post("/revoke")
    void revokeToken(
            @NonNull @QueryValue("client_id") String clientId,
            @NonNull @QueryValue("token") String token
            );

    @Post("/token?grant_type=client_credentials")
    @NonNull TwitchToken getApplicationToken(
            @NonNull @QueryValue("client_id") String clientId,
            @NonNull @QueryValue("client_secret") String clientSecret
    );

    @Get("https://id.twitch.tv/oauth2/validate")
    @NonNull TwitchValidation validate(@NonNull @Header String authorization);


}
