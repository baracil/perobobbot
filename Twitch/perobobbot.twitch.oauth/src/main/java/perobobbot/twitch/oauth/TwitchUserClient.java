package perobobbot.twitch.oauth;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.client.annotation.Client;
import perobobbot.twitch.api.Twitch;
import perobobbot.twitch.oauth.dto.UsersDTO;

@Client(Twitch.HELIX_URL)
public interface TwitchUserClient {

    @Get("/users")
    UsersDTO getUsers(@Header(name = "Authorization") String authorization,
                      @Header(name = "Client-Id") String clientId);
}
