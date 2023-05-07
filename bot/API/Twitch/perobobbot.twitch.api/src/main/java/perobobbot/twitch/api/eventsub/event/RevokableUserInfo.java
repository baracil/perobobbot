package perobobbot.twitch.api.eventsub.event;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.annotation.Nullable;
import lombok.Value;
import perobobbot.twitch.api.UserInfo;

import java.util.Optional;

@Value
@Serdeable
public class RevokableUserInfo {

    String id;
    @Nullable String login;
    @Nullable String name;

    public Optional<String> getLogin() {
        return Optional.ofNullable(login);
    }
    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }

    public Optional<UserInfo> asNotRevoked() {
        if (login == null || name == null) {
            return Optional.empty();
        }
        return Optional.of(new UserInfo(id, login, name));
    }
}
