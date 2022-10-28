package perobobbot.api;

import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;
import perobobbot.api.data.Platform;

@Serdeable
public record Identification(@NonNull Platform platform, @NonNull String userId) {

    public boolean isSameIdentity(@NonNull Platform platform, @NonNull String userId) {
        return platform.equals(this.platform) && userId.equals(this.userId);
    }


    public void checkSameIdentity(@NonNull Identification other) {
        if (other.equals(this)) {
            return;
        }
        throw new IncompatibleIdentification(this, other);
    }

    public void checkSameIdentity(@NonNull Identification.Provider other) {
        checkSameIdentity(other.identification());
    }


    public interface Provider {
        @NonNull Identification identification();

        default void checkSameIdentity(@NonNull Identification.Provider other) {
            identification().checkSameIdentity(other);
        }

    }
}
