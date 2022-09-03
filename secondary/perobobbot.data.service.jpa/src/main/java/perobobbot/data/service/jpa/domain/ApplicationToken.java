package perobobbot.data.service.jpa.domain;

import fpc.tools.cipher.TextDecryptor;
import fpc.tools.persistence.SimplePersistentObject;
import lombok.*;
import perobobbot.data.io.view.ApplicationTokenView;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.time.Instant;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter(AccessLevel.PROTECTED)
public class ApplicationToken extends SimplePersistentObject {

    @OneToOne(orphanRemoval = true)
    private @NonNull Application application;

    private @NonNull String accessToken;

    private @NonNull Instant expirationInstant;

    public @NonNull ApplicationTokenView toView(@NonNull TextDecryptor cipher) {
        return new ApplicationTokenView(application.getPlatform(), cipher.decrypt(accessToken), expirationInstant);
    }

}
