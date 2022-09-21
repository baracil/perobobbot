package perobobbot.service.jpa.domain;

import com.google.common.collect.ImmutableList;
import lombok.*;
import org.hibernate.annotations.Type;
import perobobbot.api.data.Platform;
import perobobbot.api.data.view.EncryptedUserToken;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "USER_TOKEN")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter(AccessLevel.PROTECTED)
public class UserTokenEntity extends BaseEntity {

    @Column(name = "PLATFORM", nullable = false)
    @Type(type = "perobobbot.service.jpa.domain.PlatformType")
    private @NonNull Platform platform;

    @Column(name = "ACCESS_TOKEN", columnDefinition = "VARCHAR", nullable = false)
    private @NonNull String accessToken;

    @Column(name = "REFRESH_TOKEN", columnDefinition = "VARCHAR", nullable = false)
    private @NonNull String refreshToken;

    @Column(name = "EXPIRATION_INSTANT", nullable = false)
    private @NonNull Instant expirationInstant;

    @Column(name = "SCOPES", columnDefinition = "VARCHAR", nullable = false)
    private @NonNull String scopes;

    @Column(name = "TOKEN_TYPE", columnDefinition = "VARCHAR", nullable = false)
    private @NonNull String tokenType;

    public static UserTokenEntity createWith(EncryptedUserToken token) {
        return new UserTokenEntity(
                token.platform(),
                token.accessToken(),
                token.refreshToken(),
                token.expiringInstant(),
                String.join(",", token.scopes()),
                token.tokenType()
        );
    }

    public @NonNull EncryptedUserToken toView() {
        return new EncryptedUserToken(
                platform,
                accessToken,
                refreshToken,
                expirationInstant,
                ImmutableList.copyOf(scopes.split(",")),
                tokenType
        );
    }

    public void updateWith(@NonNull EncryptedUserToken encryptedToken) {
        this.accessToken = encryptedToken.accessToken();
        this.refreshToken = encryptedToken.refreshToken();
        this.expirationInstant = encryptedToken.expiringInstant();
        this.scopes = String.join(",", encryptedToken.scopes());
        this.tokenType = encryptedToken.tokenType();
    }
}
