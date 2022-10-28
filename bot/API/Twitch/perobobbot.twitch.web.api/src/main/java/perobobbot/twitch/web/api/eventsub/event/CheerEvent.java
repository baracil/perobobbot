package perobobbot.twitch.web.api.eventsub.event;

import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.api.TwitchApiPayload;
import perobobbot.twitch.web.api.UserInfo;

import java.util.Optional;

@Value
public class CheerEvent implements BroadcasterProvider, EventSubEvent, TwitchApiPayload {
    UserInfo user;
    @NonNull UserInfo broadcaster;
    boolean anonymous;
    @NonNull String message;
    int bits;

    public @NonNull Optional<UserInfo> getUser() {
        return Optional.ofNullable(user);
    }
}
