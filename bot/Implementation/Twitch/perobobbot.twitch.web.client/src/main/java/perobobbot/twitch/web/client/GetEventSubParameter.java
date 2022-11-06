package perobobbot.twitch.web.client;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.annotation.QueryValue;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@Builder
@Introspected
@RequiredArgsConstructor
public class GetEventSubParameter {
    @QueryValue("status")
    String status;
    @QueryValue("type")
    String type;
    @QueryValue("user_id")
    String user_id;
    @QueryValue("after")
    String after;

}
