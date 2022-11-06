package perobobbot.twitch.api.eventsub;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;
import lombok.Value;

@Value
@Introspected
@Serdeable
public class Transport {

    @NonNull TransportMethod method;
    @NonNull String callback;
}
