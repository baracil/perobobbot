package perobobbot.api.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;
import perobobbot.api.Identity;
import perobobbot.api.UserInfo;

import java.util.List;

@Serdeable
public record UserIdentity(long id,
                           @NonNull Identity identity,
                           @NonNull UserType userType,
                           @NonNull String login, @NonNull String name,
                           @NonNull List<JoinedChannel> joinedChannels) {

    @JsonIgnore
    public @NonNull Platform platform() {
        return identity.platform();
    }

    @JsonIgnore
    public @NonNull String userId() {
        return identity.userId();
    }


    public UserInfo toUserInfo() {
        return new UserInfo(identity,login,name);
    }
}
