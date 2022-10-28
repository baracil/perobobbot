package perobobbot.twitch.oauth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;
import lombok.Value;
import perobobbot.api.Identification;
import perobobbot.api.UserInfo;
import perobobbot.twitch.api.Twitch;

@Value
@Serdeable
@Introspected
public class UserDTO {
    @JsonProperty("id")
    @NonNull String id;
    @JsonProperty("login")
    @NonNull String login;
    @JsonProperty("display_name")
    @NonNull String displayName;

    public @NonNull Identification getIdentification() {
        return new Identification(Twitch.PLATFORM, id);
    }

    public @NonNull UserInfo toUserInfo() {
        return new UserInfo(getIdentification(), login, displayName);
    }
}
