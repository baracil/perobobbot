package perobobbot.twitch.api.eventsub.event;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Value;
import perobobbot.twitch.api.TwitchApiPayload;
import perobobbot.twitch.api.UserInfo;

import java.util.Optional;

@Value
@Serdeable
public class UserUpdateEvent implements EventSubEvent, TwitchApiPayload {
    UserInfo user;
    @Nullable String email;
    String description;

    public Optional<String> getEmail() {
        return Optional.ofNullable(email);
    }

}
