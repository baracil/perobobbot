package perobobbot.service.jpa.domain;

import lombok.*;
import perobobbot.api.data.Platform;
import perobobbot.api.data.view.UserIdentity;
import perobobbot.api.data.view.UserToken;

import javax.persistence.*;

@Entity
@Table(name = "USER_IDENTITY", uniqueConstraints = {@UniqueConstraint(columnNames = {"PLATFORM","USER_ID"})})
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter @Setter(AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"platform","userId"})
public class UserIdentityEntity extends BaseEntity {

    @Column(name = "PLATFORM", nullable = false)
    @Convert(converter = PlatformConverter.class)
    private @NonNull Platform platform;

    @Column(name = "USER_ID",nullable = false)
    private @NonNull String userId;

    @Column(name = "LOGIN",nullable = false)
    private @NonNull String login;

    @Column(name = "NAME",nullable = false)
    private @NonNull String name;

    @OneToOne(mappedBy = "userIdentity", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserTokenEntity tokenEntity;

    public static @NonNull UserIdentityEntity createWith(@NonNull UserIdentity userIdentity) {
        return new UserIdentityEntity(userIdentity.platform(),userIdentity.userId(),userIdentity.login(),userIdentity.name(),null);
    }

    public @NonNull UserIdentity toView() {
        return new UserIdentity(
                platform,
                userId,
                login,
                name
        );
    }

    public @NonNull UserIdentityEntity updateToken(@NonNull UserToken.Encrypted userToken) {
        if (!userToken.identity().equals(toView())) {
            throw new IllegalArgumentException("Incompatible token and identity "+this.toView()+" "+userToken.identity());
        }
        final var main = this.tokenEntity != null && this.tokenEntity.isMain();
        this.tokenEntity = UserTokenEntity.createWith(this,userToken,main);
        return this;
    }


}
