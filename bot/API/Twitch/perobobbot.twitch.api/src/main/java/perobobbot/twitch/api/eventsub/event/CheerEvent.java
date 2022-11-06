package perobobbot.twitch.api.eventsub.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.api.TwitchApiPayload;
import perobobbot.twitch.api.UserInfo;

import java.util.Optional;

@Value
@Serdeable
public class CheerEvent implements BroadcasterProvider, EventSubEvent, TwitchApiPayload {
    UserInfo user;
    @NonNull UserInfo broadcaster;
    @JsonProperty("is_anonymous")
    boolean anonymous;
    @NonNull String message;
    int bits;

    public @NonNull Optional<UserInfo> getUser() {
        return Optional.ofNullable(user);
    }
}
