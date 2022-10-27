package perobobbot.twitch.api.eventsub.event;

import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.api.TwitchApiPayload;


@Value
public class UserAuthorizationRevokeEvent implements EventSubEvent, TwitchApiPayload {

    @NonNull String clientId;
    @NonNull RevokableUserInfo user;

}
