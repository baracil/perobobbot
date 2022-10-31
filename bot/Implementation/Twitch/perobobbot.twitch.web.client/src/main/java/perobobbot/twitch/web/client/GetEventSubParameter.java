package perobobbot.twitch.web.client;

import io.micronaut.http.annotation.QueryValue;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@Builder
@Serdeable
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
