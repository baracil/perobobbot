package perobobbot.data.service.jpa.domain;

import com.google.common.collect.ImmutableList;
import fpc.tools.cipher.TextDecryptor;
import fpc.tools.persistence.SimplePersistentObject;
import lombok.*;
import perobobbot.data.io.view.UserTokenView;

import javax.persistence.Entity;
import java.time.Instant;

@Entity
@NoArgsConstructor
@Getter @Setter(AccessLevel.PROTECTED)
public class UserToken extends SimplePersistentObject {

    private @NonNull String platform;

    private @NonNull String accessToken;

    private @NonNull String refreshToken;

    private @NonNull Instant expiringInstant;

    private @NonNull String scopes;

    private @NonNull String tokenType;

    public @NonNull UserTokenView toView(@NonNull TextDecryptor textDecrypter) {
        return new UserTokenView(
                platform,
                textDecrypter.decrypt(accessToken),
                textDecrypter.decrypt(refreshToken),
                expiringInstant,
                ImmutableList.copyOf(scopes.split(",")),
                tokenType
        );
    }
}
