package perobobbot.twitch.api.eventsub.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Value;

@Value
@Serdeable
public class Limit {
    @JsonProperty("is_enabled")
    boolean enabled;
    int value;
}
