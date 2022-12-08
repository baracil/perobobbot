package perobobbot.twitch.api.eventsub;

import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;
import lombok.Value;

@Value
@Serdeable
public class Transport {

    @NonNull TransportMethod method;
    @NonNull String callback;
}
