package perobobbot.twitch.api;

import io.micronaut.serde.annotation.Serdeable;
import lombok.Value;

import java.util.List;

@Serdeable
@Value
public class Users {
    List<User> data;

}
