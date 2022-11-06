package perobobbot.twitch.api.eventsub;

import io.micronaut.serde.annotation.Serdeable;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
@Builder
@Serdeable
public class TransportRequest {

    @NonNull TransportMethod method;
    @NonNull String callback;
    @NonNull String secret;

}
