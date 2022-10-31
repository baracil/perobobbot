package perobobbot.twitch.oauth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableSet;
import fpc.tools.lang.Secret;
import fpc.tools.lang.Todo;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.annotation.Nullable;
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

@Introspected
@Value
@Serdeable
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


    private @NonNull ImmutableSet<Scope> prepareScopes() {
        if (scope == null) {
            return ImmutableSet.of();
        }

        return Arrays.stream(scope)
                     .map(TwitchScope::findScopeByName)
                     .flatMap(Optional::stream)
                     .collect(ImmutableSet.toImmutableSet());

    }
}
