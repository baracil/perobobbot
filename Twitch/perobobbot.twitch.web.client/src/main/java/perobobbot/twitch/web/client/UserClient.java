package perobobbot.twitch.web.client;

import io.micronaut.http.client.annotation.Client;
import perobobbot.twitch.api.Twitch;

@Client(Twitch.HELIX_URL)
public interface UserClient {


}
