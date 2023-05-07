package perobobbot.twitch.oauth;

import com.fasterxml.jackson.annotation.JsonAlias;
import fpc.tools.lang.Secret;
import io.micronaut.core.annotation.Introspected;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;
import perobobbot.api.data.UserToken;

@Introspected
@Value
public class TwitchRefreshedToken {

    @JsonAlias("access_token") String accessToken;
    @JsonAlias("refresh_token") String refreshToken;
    @Getter(AccessLevel.NONE)
    @JsonAlias("scope") String[] scopes;

    public UserToken<Secret> update(UserToken<Secret> expiredUserToken) {
        return expiredUserToken.toBuilder()
                               .accessToken(Secret.of(this.accessToken))
                               .refreshToken(Secret.of(this.refreshToken))
                               .build();
    }

//    public RefreshedToken toRefreshedToken() {
//        return new RefreshedToken(Secret.with(accessToken),Secret.with(refreshToken));
//    }

}
