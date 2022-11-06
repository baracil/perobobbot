package perobobbot.twitch.web.client;

import io.micronaut.core.annotation.Introspected;
import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.api.ChannelInformation;

@Value
@Introspected
public class GetChannelInformationResponse {

    @NonNull ChannelInformation[] data;

}
