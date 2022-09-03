package perobobbot.data.service.jpa.domain;

import fpc.tools.cipher.TextDecryptor;
import fpc.tools.persistence.SimplePersistentObject;
import lombok.*;
import perobobbot.data.io.view.ApplicationView;

import javax.persistence.*;
import java.time.Instant;

@Entity
@NoArgsConstructor
@Getter @Setter(AccessLevel.PROTECTED)
@Table(uniqueConstraints = {@UniqueConstraint(name = "UC_PLATFORM",columnNames = {"platform"})})
public class Application extends SimplePersistentObject {

    @Column(nullable = false, columnDefinition = "text")
    private @NonNull String platform;

    @Column(nullable = false, columnDefinition = "text")
    @Setter
    private @NonNull String name;

    @Column(nullable = false, columnDefinition = "text")
    @Setter
    private @NonNull String clientId;

    @Column(nullable = false, columnDefinition = "text")
    @Setter
    private @NonNull String clientSecret;

    @OneToOne(mappedBy = "application",cascade = CascadeType.ALL)
    private ApplicationToken token;

    public Application(@NonNull String platform, @NonNull String name, @NonNull String clientId, @NonNull String clientSecret) {
        this.platform = platform;
        this.name = name;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public @NonNull ApplicationToken setToken(@NonNull String accessToken, @NonNull Instant expirationInstant) {
        final var token = new ApplicationToken(this,accessToken,expirationInstant);
        this.token = token;
        return token;
    }

    public @NonNull ApplicationView toView(@NonNull TextDecryptor textDecryptor) {
        return new ApplicationView(platform,name,clientId,textDecryptor.decrypt(clientSecret));
    }
}
