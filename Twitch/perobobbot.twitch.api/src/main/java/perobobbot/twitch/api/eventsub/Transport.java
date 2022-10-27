package perobobbot.twitch.api.eventsub;

import lombok.NonNull;
import lombok.Value;

@Value
public class Transport {

    @NonNull TransportMethod method;
    @NonNull String callback;
}
