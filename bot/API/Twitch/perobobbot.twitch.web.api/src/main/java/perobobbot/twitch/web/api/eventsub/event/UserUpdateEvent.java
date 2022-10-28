package perobobbot.twitch.web.api.eventsub.event;

import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.web.api.UserInfo;

import java.util.Optional;

@Value
public class UserUpdateEvent implements EventSubEvent {
    @NonNull UserInfo user;
    String email;
    @NonNull String description;

    public @NonNull Optional<String> getEmail() {
        return Optional.ofNullable(email);
    }

}
