package perobobbot.service.jpa.domain;

import lombok.*;
import perobobbot.api.data.Platform;
import perobobbot.api.data.view.Application;

import javax.persistence.*;
import java.time.Instant;

@Entity
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@Getter @Setter(AccessLevel.PROTECTED)
@Table(name = "APPLICATION")
public class ApplicationEntity extends BaseEntity {

    @Column(name = "PLATFORM", nullable = false,unique = true)
    @Convert(converter = PlatformConverter.class)
    private @NonNull Platform platform;

    @Column(name = "NAME", nullable = false)
    @Setter
    private @NonNull String name;

    @Column(name = "CLIENT_ID",nullable = false)
    @Setter
    private @NonNull String clientId;

    @Column(name = "CLIENT_SECRET", nullable = false)
    @Setter
    private @NonNull String clientSecret;

    @OneToOne(mappedBy = "application",cascade = CascadeType.ALL)
    private ApplicationTokenEntity token;

    public ApplicationEntity(@NonNull Platform platform, @NonNull String name, @NonNull String clientId, @NonNull String clientSecret) {
        this.platform = platform;
        this.name = name;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public @NonNull ApplicationTokenEntity setToken(@NonNull String accessToken, @NonNull Instant expirationInstant) {
        final var token = new ApplicationTokenEntity(this,accessToken,expirationInstant);
        this.token = token;
        return token;
    }

    public @NonNull Application.Encrypted toView() {
        return new Application.Encrypted(platform,name,clientId,clientSecret);
    }
}
