package perobobbot.twitch.oauth.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;
import lombok.Value;
import perobobbot.api.Identity;
import perobobbot.api.UserInfo;
import perobobbot.twitch.api.Twitch;

@Value
@Serdeable
public class UserDTO {
    @JsonProperty("id")
    @NonNull String id;
    @JsonProperty("login")
    @NonNull String login;
    @JsonProperty("display_name")
    @NonNull String displayName;

    @JsonIgnore
    public @NonNull Identity getIdentification() {
        return new Identity(Twitch.PLATFORM, id,login);
    }

    @JsonIgnore
public @NonNull UserInfo toUserInfo() {
        return new UserInfo(getIdentification(), login, displayName);
    }
}
