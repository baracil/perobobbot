package perobobbot.twitch.api;

import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;
import lombok.Value;

@Value
@Serdeable
public class UserInfo {

    @NonNull String id;
    @NonNull String login;
    @NonNull String name;

}

