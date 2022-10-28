package perobobbot.twitch.web.client;

import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.api.ChannelInformation;

@Value
@Serdeable
public class GetChannelInformationResponse {

    @NonNull ChannelInformation[] data;

}
