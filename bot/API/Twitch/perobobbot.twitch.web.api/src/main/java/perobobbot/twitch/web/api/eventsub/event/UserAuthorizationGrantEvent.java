package perobobbot.twitch.web.api.eventsub.event;

import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.api.TwitchApiPayload;
import perobobbot.twitch.web.api.UserInfo;


@Value
public class UserAuthorizationGrantEvent implements EventSubEvent, TwitchApiPayload {

    @NonNull String clientId;
    @NonNull UserInfo user;

}
