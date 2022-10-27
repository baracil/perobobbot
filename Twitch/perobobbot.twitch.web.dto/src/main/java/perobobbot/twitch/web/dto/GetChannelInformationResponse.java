package perobobbot.twitch.web.dto;

import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.api.channel.ChannelInformation;

@Value
@Serdeable
public class GetChannelInformationResponse {

    @NonNull ChannelInformation[] data;

}
