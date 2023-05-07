package perobobbot.domain.jpa.entity;

import lombok.*;
import org.hibernate.annotations.Type;
import perobobbot.api.Scope;
import perobobbot.api.data.Platform;
import perobobbot.api.data.UserToken;

import javax.persistence.*;
import java.time.Instant;
import java.util.stream.Collectors;

@Entity
@Table(name = "USER_TOKEN")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter(AccessLevel.PROTECTED)
public class UserTokenEntity extends BaseEntity {

    @JoinColumn(name = "USER_IDENTITY_ID",nullable = false, foreignKey = @ForeignKey(name = "FK_TOKEN__USER_IDENTITY"))
    @OneToOne
    private UserIdentityEntity userIdentity;

    @Column(name = "PLATFORM", nullable = false)
    @Type(type = "perobobbot.domain.jpa.entity.PlatformType")
    private Platform platform;

    @Column(name = "ACCESS_TOKEN", columnDefinition = "VARCHAR", nullable = false)
    private String accessToken;

    @Column(name = "REFRESH_TOKEN", columnDefinition = "VARCHAR")
    private String refreshToken;

    @Column(name = "EXPIRATION_INSTANT", nullable = false)
    private Instant expirationInstant;

    @Column(name = "SCOPES", columnDefinition = "VARCHAR", nullable = false)
    private String scopes;

    @Column(name = "TOKEN_TYPE", columnDefinition = "VARCHAR", nullable = false)
    private String tokenType;

    public static UserTokenEntity createWith(UserIdentityEntity userIdentity,UserToken.Encrypted userToken) {
        return new UserTokenEntity(
                userIdentity,
                userToken.platform(),
                userToken.accessToken(),
                userToken.refreshToken(),
                userToken.expirationInstant(),
                userToken.scopes().stream().map(Scope::getName).collect(Collectors.joining(",")),
                userToken.tokenType()
        );
    }

    public UserToken.Encrypted toView() {
        return new UserToken.Encrypted(
                userIdentity.createIdentification(),
                accessToken,
                refreshToken,
                expirationInstant,
                Scope.splitScopes(scopes,','),
                tokenType
        );
    }

    public void updateWith(UserToken<String> encryptedUserToken) {
        this.accessToken = encryptedUserToken.accessToken();
        this.refreshToken = encryptedUserToken.refreshToken();
        this.expirationInstant = encryptedUserToken.expirationInstant();
        this.scopes = Scope.joinScopeNames(encryptedUserToken.scopes(),',');
        this.tokenType = encryptedUserToken.tokenType();
    }
}
