package perobobbot.api.data;

import fpc.tools.cipher.Decryptable;
import fpc.tools.cipher.Encryptable;
import fpc.tools.cipher.TextDecryptor;
import fpc.tools.cipher.TextEncryptor;
import fpc.tools.lang.Secret;
import io.micronaut.core.annotation.Introspected;
import perobobbot.api.Identity;
import perobobbot.api.Scope;

import java.time.Instant;
import java.util.Set;

public sealed interface UserToken<T>
        extends Decryptable<UserToken.Decrypted>, Encryptable<UserToken.Encrypted>, Identity.Provider
        permits UserToken.Encrypted, UserToken.Decrypted {

    Identity identity();

    T accessToken();

    T refreshToken();

    Instant expirationInstant();

    Set<Scope> scopes();

    String tokenType();

    UserTokenBuilder<T> toBuilder();

    default Platform platform() {
        return identity().platform();
    }

    @Introspected
    record Decrypted(Identity identity,
                     Secret accessToken,
                     Secret refreshToken, Instant expirationInstant,
                     Set<Scope> scopes,
                     String tokenType) implements UserToken<Secret>, AccessTokenProvider {

        @Override
        public UserTokenBuilder<Secret> toBuilder() {
            return UserTokenBuilder.forDecrypted(this);
        }

        @Override
        public UserToken.Encrypted encrypt(TextEncryptor textEncryptor) {
            return new Encrypted(
                    identity,
                    textEncryptor.encrypt(accessToken),
                    textEncryptor.encrypt(refreshToken),
                    expirationInstant,
                    scopes,
                    tokenType);
        }

        @Override
        public UserToken.Decrypted decrypt(TextDecryptor textDecryptor) {
            return this;
        }

        @Override
        public <T> T accept(Visitor<T> visitor) {
            return visitor.visit(this);
        }
    }

    @Introspected
    record Encrypted(Identity identity,
                     String accessToken,
                     String refreshToken, Instant expirationInstant,
                     Set<Scope> scopes, String tokenType) implements UserToken<String> {
        @Override
        public UserTokenBuilder<String> toBuilder() {
            return UserTokenBuilder.forEncrypted(this);
        }

        @Override
        public UserToken.Encrypted encrypt(TextEncryptor textEncryptor) {
            return this;
        }

        @Override
        public UserToken.Decrypted decrypt(TextDecryptor textDecryptor) {
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
