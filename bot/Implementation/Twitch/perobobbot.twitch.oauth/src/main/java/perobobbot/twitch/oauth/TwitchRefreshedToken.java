package perobobbot.twitch.oauth;

import com.fasterxml.jackson.annotation.JsonAlias;
import fpc.tools.lang.Secret;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import perobobbot.api.data.UserToken;

@Introspected
@Value
@Serdeable
public class TwitchRefreshedToken {

    @JsonAlias("access_token") @NonNull String accessToken;
    @JsonAlias("refresh_token") @NonNull String refreshToken;
    @Getter(AccessLevel.NONE)
    @JsonAlias("scope") String[] scopes;

    public @NonNull UserToken<Secret> update(@NonNull UserToken<Secret> expiredUserToken) {
        return expiredUserToken.toBuilder()
                               .accessToken(Secret.of(this.accessToken))
                               .refreshToken(Secret.of(this.refreshToken))
                               .build();
    }

//    public @NonNull RefreshedToken toRefreshedToken() {
//        return new RefreshedToken(Secret.with(accessToken),Secret.with(refreshToken));
//    }

}
