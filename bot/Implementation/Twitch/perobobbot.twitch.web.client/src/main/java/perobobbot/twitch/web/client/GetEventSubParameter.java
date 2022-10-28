package perobobbot.twitch.web.client;

import io.micronaut.http.annotation.QueryValue;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Value;

@Value
@Serdeable
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
