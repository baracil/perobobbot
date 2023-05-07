package perobobbot.twitch.api.eventsub;

import io.micronaut.serde.annotation.Serdeable;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
@Builder
@Serdeable
public class TransportRequest {

    TransportMethod method;
    String callback;
    String secret;

}
