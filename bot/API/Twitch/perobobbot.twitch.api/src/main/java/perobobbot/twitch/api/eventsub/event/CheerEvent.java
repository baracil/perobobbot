package perobobbot.twitch.api.eventsub.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.annotation.Nullable;
import lombok.Value;
import perobobbot.twitch.api.TwitchApiPayload;
import perobobbot.twitch.api.UserInfo;

import java.util.Optional;

@Value
@Serdeable
public class CheerEvent implements BroadcasterProvider, EventSubEvent, TwitchApiPayload {
    @Nullable
    UserInfo user;
    UserInfo broadcaster;
    @JsonProperty("is_anonymous")
    boolean anonymous;
    String message;
    int bits;

    public Optional<UserInfo> getUser() {
        return Optional.ofNullable(user);
    }
}
