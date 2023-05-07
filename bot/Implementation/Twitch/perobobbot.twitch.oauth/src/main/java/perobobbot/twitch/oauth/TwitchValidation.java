package perobobbot.twitch.oauth;

import com.fasterxml.jackson.annotation.JsonAlias;
import io.micronaut.core.annotation.Introspected;
import jakarta.annotation.Nullable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;

import java.util.Optional;

@Introspected
@Value
public class TwitchValidation {

    @JsonAlias("client_id")
    String clientId;
    @Getter(AccessLevel.NONE)
    @JsonAlias("login")
    @Nullable String login;
    @Getter(AccessLevel.NONE)
    @JsonAlias("scopes")
    @Nullable String [] scopes;
    @Getter(AccessLevel.NONE)
    @JsonAlias("user_id")
    @Nullable String userId;
    @JsonAlias("expires_in")
    int expiresIn;

    public Optional<String> getLogin() {
        return Optional.ofNullable(login);
    }

    public Optional<String> getUserId() {
        return Optional.ofNullable(userId);
    }

    public String[] getScopes() {
        return scopes == null ? new String[0] : scopes;
    }

//    public UserInfo toUserInfo() {
//        return new UserInfo(new Identity(Twitch.PLATFORM, userId), login, login);
//    }
}
