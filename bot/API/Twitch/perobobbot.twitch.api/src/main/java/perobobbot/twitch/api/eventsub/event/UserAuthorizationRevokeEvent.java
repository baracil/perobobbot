package perobobbot.twitch.api.eventsub.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.api.TwitchApiPayload;


@Value
@Serdeable
public class UserAuthorizationRevokeEvent implements EventSubEvent, TwitchApiPayload {

    @JsonProperty("client_id")
    @NonNull String clientId;
    @NonNull RevokableUserInfo user;

}
