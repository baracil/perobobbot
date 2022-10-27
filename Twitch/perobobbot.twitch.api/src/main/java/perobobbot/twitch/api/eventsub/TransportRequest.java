package perobobbot.twitch.api.eventsub;

import lombok.NonNull;
import lombok.Value;

@Value
public class TransportRequest {

    @NonNull TransportMethod method;
    @NonNull String callback;
    @NonNull String secret;

}
