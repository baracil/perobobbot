package perobobbot.domain.jpa.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import perobobbot.api.data.Application;
import perobobbot.api.data.Platform;

import javax.persistence.*;
import java.time.Instant;

@Entity
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@Getter @Setter(AccessLevel.PROTECTED)
@Table(name = "APPLICATION", uniqueConstraints = {
        @UniqueConstraint(name = "uk_platform", columnNames = {"PLATFORM"})
})
public class ApplicationEntity extends BaseEntity {

    @Column(name = "PLATFORM", nullable = false)
    @Convert(converter = PlatformConverter.class)
    private Platform platform;

    @Column(name = "NAME", nullable = false)
    @Setter
    private String name;

    @Column(name = "CLIENT_ID",nullable = false)
    @Setter
    private String clientId;

    @Column(name = "CLIENT_SECRET", nullable = false)
    @Setter
    private String clientSecret;

    @OneToOne(mappedBy = "application",cascade = CascadeType.ALL)
    private ApplicationTokenEntity token;

    public ApplicationEntity(Platform platform, String name, String clientId, String clientSecret) {
        this.platform = platform;
        this.name = name;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public ApplicationTokenEntity setToken(String accessToken, Instant expirationInstant) {
        final var token = new ApplicationTokenEntity(this,accessToken,expirationInstant);
        this.token = token;
        return token;
    }

    public Application.Encrypted toView() {
        return new Application.Encrypted(platform,name,clientId,clientSecret);
    }
}
