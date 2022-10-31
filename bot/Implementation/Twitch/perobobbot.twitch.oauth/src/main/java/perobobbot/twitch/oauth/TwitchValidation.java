package perobobbot.twitch.oauth;

import com.fasterxml.jackson.annotation.JsonAlias;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import perobobbot.api.Identity;
import perobobbot.api.UserInfo;
import perobobbot.twitch.api.Twitch;

import java.util.Optional;

@Introspected
@Value
@Serdeable
public class TwitchValidation {

    @JsonAlias("client_id")
    @NonNull String clientId;
    @Getter(AccessLevel.NONE)
    @JsonAlias("login")
    String login;
    @Getter(AccessLevel.NONE)
    @JsonAlias("scopes")
    String[] scopes;
    @Getter(AccessLevel.NONE)
    @JsonAlias("user_id")
    String userId;
    @JsonAlias("expires_in")
    int expiresIn;

    public @NonNull Optional<String> getLogin() {
        return Optional.ofNullable(login);
    }

    public @NonNull Optional<String> getUserId() {
        return Optional.ofNullable(userId);
    }

    public @NonNull String[] getScopes() {
        return scopes == null ? new String[0] : scopes;
    }

    public @NonNull UserInfo toUserInfo() {
        return new UserInfo(new Identity(Twitch.PLATFORM, userId), login, login);
    }
}
