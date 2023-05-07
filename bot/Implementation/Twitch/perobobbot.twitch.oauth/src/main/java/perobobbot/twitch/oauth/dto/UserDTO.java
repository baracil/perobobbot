package perobobbot.twitch.oauth.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Value;
import perobobbot.api.Identity;
import perobobbot.api.UserInfo;
import perobobbot.twitch.api.Twitch;

@Value
@Serdeable
public class UserDTO {
    @JsonProperty("id")
    String id;
    @JsonProperty("login")
    String login;
    @JsonProperty("display_name")
    String displayName;

    @JsonIgnore
    public Identity getIdentification() {
        return new Identity(Twitch.PLATFORM, id);
    }

    @JsonIgnore
public UserInfo toUserInfo() {
        return new UserInfo(getIdentification(), login, displayName);
    }
}
