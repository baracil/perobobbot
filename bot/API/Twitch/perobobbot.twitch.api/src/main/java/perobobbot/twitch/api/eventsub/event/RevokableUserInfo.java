package perobobbot.twitch.api.eventsub.event;

import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.api.UserInfo;

import java.util.Optional;

@Value
@Serdeable
public class RevokableUserInfo {

    @NonNull String id;
    String login;
    String name;

    public @NonNull Optional<String> getLogin() {
        return Optional.ofNullable(login);
    }
    public @NonNull Optional<String> getName() {
        return Optional.ofNullable(name);
    }

    public @NonNull Optional<UserInfo> asNotRevoked() {
        if (login == null || name == null) {
            return Optional.empty();
        }
        return Optional.of(new UserInfo(id, login, name));
    }
}
