package perobobbot.twitch.web.client;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;
import lombok.NonNull;
import perobobbot.twitch.api.Twitch;
import perobobbot.twitch.web.client.dto.GetChannelInformationResponse;

import java.util.Set;

@Client(Twitch.HELIX_URL)
public interface ChannelClient {

    @Get("/channels")
    GetChannelInformationResponse getChannelInformation(@NonNull @QueryValue("broadcaster_id") Set<String> broadcasterId);

}
