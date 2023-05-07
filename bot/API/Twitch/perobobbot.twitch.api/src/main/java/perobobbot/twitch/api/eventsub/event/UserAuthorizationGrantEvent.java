package perobobbot.twitch.api.eventsub.event;

import io.micronaut.serde.annotation.Serdeable;
import io.micronaut.serde.config.naming.SnakeCaseStrategy;
import lombok.Value;
import perobobbot.twitch.api.TwitchApiPayload;
import perobobbot.twitch.api.UserInfo;


@Value
@Serdeable(naming = SnakeCaseStrategy.class)
public class UserAuthorizationGrantEvent implements EventSubEvent, TwitchApiPayload {

    String clientId;
    UserInfo user;

}
