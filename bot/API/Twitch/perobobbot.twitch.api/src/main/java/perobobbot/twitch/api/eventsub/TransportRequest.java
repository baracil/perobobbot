package perobobbot.twitch.api.eventsub;

import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
@Builder
public class TransportRequest {

    @NonNull TransportMethod method;
    @NonNull String callback;
    @NonNull String secret;

}
