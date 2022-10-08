package perobobbot.service.jpa.domain;

import lombok.*;
import perobobbot.api.data.view.ApplicationToken;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "APPLICATION_TOKEN")
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter @Setter(AccessLevel.PROTECTED)
public class ApplicationTokenEntity extends BaseEntity {

    /**
     * The application this token apply
     */
    @JoinColumn(name = "APPLICATION_ID",
            foreignKey = @ForeignKey(name = "FK_APPLICATION_TOKEN__APPLICATION"))
    @OneToOne(orphanRemoval = true)
    private @NonNull ApplicationEntity application;

    /**
     * The encrypted access token
     */
    @Column(name = "ACCESS_TOKEN",nullable = false)
    private @NonNull String accessToken;

    /**
     * The expiration date of the token
     */
    @Column(name = "EXPIRATION_INSTANT",nullable = false)
    private @NonNull Instant expirationInstant;

    public @NonNull ApplicationToken.Encrypted toView() {
        return new ApplicationToken.Encrypted(application.getPlatform(), accessToken, expirationInstant);
    }

}
