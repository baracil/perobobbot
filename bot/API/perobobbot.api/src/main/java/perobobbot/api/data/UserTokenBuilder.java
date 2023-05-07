package perobobbot.api.data;

import fpc.tools.lang.Secret;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import perobobbot.api.Identity;
import perobobbot.api.Scope;

import java.time.Instant;
import java.util.Set;
import java.util.function.Function;

@RequiredArgsConstructor
public class UserTokenBuilder<T> {

    public static UserTokenBuilder<String> forEncrypted(UserToken<String> reference) {
        final var builder = new UserTokenBuilder<String>(b -> b.createEncrypted(b.accessToken, b.refreshToken));
        return initialize(builder, reference);
    }

    public static UserTokenBuilder<Secret> forDecrypted(UserToken<Secret> reference) {
        final var builder = new UserTokenBuilder<Secret>(b -> b.createDecrypted(b.accessToken, b.refreshToken));
        return initialize(builder, reference);
    }

    private static <T> UserTokenBuilder<T> initialize(UserTokenBuilder<T> builder, UserToken<T> userToken) {
        return builder.identification(userToken.identity())
                      .accessToken(userToken.accessToken())
                      .refreshToken(userToken.refreshToken())
                      .expirationInstant(userToken.expirationInstant())
                      .scopes(userToken.scopes())
                      .tokenType(userToken.tokenType());
    }

    private final Function<UserTokenBuilder<T>, UserToken<T>> factory;

    private @Nullable Identity identity;
    private @Nullable Platform platform;
    private @Nullable T accessToken;
    private @Nullable T refreshToken;
    private @Nullable Instant expirationInstant;
    private @Nullable Set<Scope> scopes;
    private @Nullable String tokenType;


    public UserToken<T> build() {
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

    public UserTokenBuilder<T> scopes(Set<Scope> scopes) {
        this.scopes = scopes;
        return this;
    }

    public UserTokenBuilder<T> tokenType(String tokenType) {
        this.tokenType = tokenType;
        return this;
    }

    private UserToken.Encrypted createEncrypted(@Nullable String access, @Nullable String refresh) {
        assert this.identity != null;
        assert access != null;
        assert refresh != null;
        assert this.expirationInstant != null;
        assert this.scopes != null;
        assert this.tokenType != null;
        return new UserToken.Encrypted(this.identity, access, refresh, this.expirationInstant, this.scopes, this.tokenType);
    }

    private UserToken.Decrypted createDecrypted(@Nullable Secret access, @Nullable Secret refresh) {
        assert this.identity != null;
        assert access != null;
        assert refresh != null;
        assert this.expirationInstant != null;
        assert this.scopes != null;
        assert this.tokenType != null;
        return new UserToken.Decrypted(this.identity, access, refresh, this.expirationInstant, this.scopes, this.tokenType);
    }
}
