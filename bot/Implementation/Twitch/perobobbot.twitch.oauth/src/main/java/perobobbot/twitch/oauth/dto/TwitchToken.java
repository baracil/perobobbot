package perobobbot.twitch.oauth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import fpc.tools.lang.Secret;
import fpc.tools.lang.Todo;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import perobobbot.api.Identity;
import perobobbot.api.Scope;
import perobobbot.api.data.ApplicationToken;
import perobobbot.api.data.UserToken;
import perobobbot.twitch.api.Twitch;
import perobobbot.twitch.api.TwitchScope;

import java.time.Instant;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Serdeable
@Value
public class TwitchToken {

    @JsonProperty("access_token")
    @NonNull String accessToken;
    @JsonProperty("refresh_token")
    @Nullable
    String refreshToken;
    @JsonProperty("expires_in")
    double expiresIn;

    @JsonProperty("scope")
    @Getter(AccessLevel.NONE)
    @Nullable
    String[] scope;
    @JsonProperty("token_type")
    @NonNull String tokenType;

    public @NonNull ApplicationToken.Decrypted toAppToken(@NonNull Instant now) {
        return new ApplicationToken.Decrypted(Twitch.PLATFORM, Secret.of(accessToken), now.plusSeconds((long) expiresIn));
    }

    public @NonNull UserToken.Decrypted toUserToken(@NonNull Identity identity, @NonNull Instant now) {

        final var twitchScopes = prepareScopes();
        final long duration = (long) expiresIn;

        if (refreshToken == null) {
            return Todo.TODO();
        }

        return new UserToken.Decrypted(
                identity,
                Secret.of(accessToken),
                Secret.of(refreshToken),
                now.plusSeconds(duration),
                twitchScopes,
                tokenType)
                ;
    }


    private @NonNull Set<Scope> prepareScopes() {
        if (scope == null) {
            return Set.of();
        }

        return Arrays.stream(scope)
                     .map(TwitchScope::findScopeByName)
                     .flatMap(Optional::stream)
                     .collect(Collectors.toSet());

    }
}
