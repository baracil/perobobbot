package perobobbot.api.data;

import fpc.tools.cipher.Decryptable;
import fpc.tools.cipher.Encryptable;
import fpc.tools.cipher.TextDecryptor;
import fpc.tools.cipher.TextEncryptor;
import fpc.tools.lang.Secret;
import io.micronaut.core.annotation.Introspected;
import lombok.NonNull;
import perobobbot.api.Identity;
import perobobbot.api.Scope;

import java.time.Instant;
import java.util.Set;

public sealed interface UserToken<T>
        extends Decryptable<UserToken.Decrypted>, Encryptable<UserToken.Encrypted>, Identity.Provider
        permits UserToken.Encrypted, UserToken.Decrypted {

    @NonNull Identity identity();

    @NonNull T accessToken();

    @NonNull T refreshToken();

    @NonNull Instant expirationInstant();

    @NonNull Set<Scope> scopes();

    @NonNull String tokenType();

    @NonNull UserTokenBuilder<T> toBuilder();

    default @NonNull Platform platform() {
        return identity().platform();
    }

    @Introspected
    record Decrypted(@NonNull Identity identity,
                     @NonNull Secret accessToken,
                     @NonNull Secret refreshToken, @NonNull Instant expirationInstant,
                     @NonNull Set<Scope> scopes,
                     @NonNull String tokenType) implements UserToken<Secret>, AccessTokenProvider {

        @Override
        public @NonNull UserTokenBuilder<Secret> toBuilder() {
            return UserTokenBuilder.forDecrypted(this);
        }

        @Override
        public @NonNull UserToken.Encrypted encrypt(@NonNull TextEncryptor textEncryptor) {
            return new Encrypted(
                    identity,
                    textEncryptor.encrypt(accessToken),
                    textEncryptor.encrypt(refreshToken),
                    expirationInstant,
                    scopes,
                    tokenType);
        }

        @Override
        public @NonNull UserToken.Decrypted decrypt(@NonNull TextDecryptor textDecryptor) {
            return this;
        }

        @Override
        public <T> @NonNull T accept(@NonNull Visitor<T> visitor) {
            return visitor.visit(this);
        }
    }

    @Introspected
    record Encrypted(@NonNull Identity identity,
                     @NonNull String accessToken,
                     @NonNull String refreshToken, @NonNull Instant expirationInstant,
                     @NonNull Set<Scope> scopes, @NonNull String tokenType) implements UserToken<String> {
        @Override
        public @NonNull UserTokenBuilder<String> toBuilder() {
            return UserTokenBuilder.forEncrypted(this);
        }

        @Override
        public @NonNull UserToken.Encrypted encrypt(@NonNull TextEncryptor textEncryptor) {
            return this;
        }

        @Override
        public @NonNull UserToken.Decrypted decrypt(@NonNull TextDecryptor textDecryptor) {
            return new Decrypted(
                    identity,
                    textDecryptor.decrypt(accessToken),
                    textDecryptor.decrypt(refreshToken),
                    expirationInstant,
                    scopes,
                    tokenType
            );
        }
    }
}
