package perobobbot.twitch.web.client;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;
import perobobbot.twitch.api.Twitch;

import java.util.Set;

@Client(Twitch.HELIX_URL)
public interface ChannelClient {

    @Get("/channels")
    GetChannelInformationResponse getChannelInformation(@QueryValue("broadcaster_id") Set<String> broadcasterId);

}
