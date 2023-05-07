package perobobbot.twitch.api;

import io.micronaut.serde.annotation.Serdeable;
import lombok.Value;

@Value
@Serdeable
public class UserInfo {

    String id;
    String login;
    String name;

}

