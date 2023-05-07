package perobobbot.api.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.micronaut.serde.annotation.Serdeable;
import perobobbot.api.Identity;
import perobobbot.api.UserInfo;

import java.util.List;

@Serdeable
public record UserIdentity(long id,
                           Identity identity,
                           UserType userType,
                           String login, String name,
                           List<JoinedChannel> joinedChannels) {

    @JsonIgnore
    public Platform platform() {
        return identity.platform();
    }

    @JsonIgnore
    public String userId() {
        return identity.userId();
    }


    public UserInfo toUserInfo() {
        return new UserInfo(identity,login,name);
    }
}
