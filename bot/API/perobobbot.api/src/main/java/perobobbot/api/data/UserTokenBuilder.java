package perobobbot.api.data;

import com.google.common.collect.ImmutableSet;
import fpc.tools.lang.Secret;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.api.Identity;
import perobobbot.api.Scope;

import java.time.Instant;
import java.util.function.Function;

@RequiredArgsConstructor
public class UserTokenBuilder<T> {

    public static UserTokenBuilder<String> forEncrypted(UserToken<String> reference) {
        final var builder = new UserTokenBuilder<String>(b -> new UserToken.Encrypted(b.identity, b.accessToken, b.refreshToken, b.expirationInstant, b.scopes, b.tokenType));
        return initialize(builder, reference);
    }

    public static UserTokenBuilder<Secret> forDecrypted(UserToken<Secret> reference) {
        final var builder = new UserTokenBuilder<Secret>(b -> new UserToken.Decrypted(b.identity, b.accessToken, b.refreshToken, b.expirationInstant, b.scopes, b.tokenType));
        return initialize(builder, reference);
    }

    private static <T> UserTokenBuilder<T> initialize(@NonNull UserTokenBuilder<T> builder, @NonNull UserToken<T> userToken) {
        return builder.identification(userToken.identity())
                      .accessToken(userToken.accessToken())
                      .refreshToken(userToken.refreshToken())
                      .expirationInstant(userToken.expirationInstant())
                      .scopes(userToken.scopes())
                      .tokenType(userToken.tokenType());
    }

    private final @NonNull Function<UserTokenBuilder<T>, UserToken<T>> factory;

    private Identity identity;
    private Platform platform;
    private T accessToken;
    private T refreshToken;
    private Instant expirationInstant;
    private ImmutableSet<Scope> scopes;
    private String tokenType;


    public @NonNull UserToken<T> build() {
        return factory.apply(this);
    }


    public UserTokenBuilder<T> identification(Identity identity) {
        this.identity = identity;
        return this;
    }

    public UserTokenBuilder<T> platform(Platform platform) {
        this.platform = platform;
        return this;
    }

    public UserTokenBuilder<T> accessToken(T accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public UserTokenBuilder<T> refreshToken(T refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    public UserTokenBuilder<T> expirationInstant(Instant expirationInstant) {
        this.expirationInstant = expirationInstant;
        return this;
    }

    public UserTokenBuilder<T> scopes(ImmutableSet<Scope> scopes) {
        this.scopes = scopes;
        return this;
    }

    public UserTokenBuilder<T> tokenType(String tokenType) {
        this.tokenType = tokenType;
        return this;
    }
}
