package perobobbot.twitch.api.eventsub.event;

import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.api.TwitchApiPayload;
import perobobbot.twitch.api.UserInfo;


@Value
public class UserAuthorizationGrantEvent implements EventSubEvent, TwitchApiPayload {

    @NonNull String clientId;
    @NonNull UserInfo user;

}
