package perobobbot.twitch.oauth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;
import lombok.Value;

@Value
@Serdeable
@Introspected
public class UsersDTO {
    @JsonProperty("data")
    @NonNull UserDTO[] users;
}
