package perobobbot.twitch.web.client;

import io.micronaut.core.annotation.Introspected;
import lombok.Value;
import perobobbot.twitch.api.ChannelInformation;

@Value
@Introspected
public class GetChannelInformationResponse {

    ChannelInformation[] data;

}
