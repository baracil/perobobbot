package perobobbot.api;

import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;
import perobobbot.api.data.Platform;

@Serdeable
public record Identification(@NonNull Platform platform, @NonNull String userId) {

    public void checkSamePerson(@NonNull Identification other) {
        if (other.equals(this)) {
            return;
        }
        throw new IncompatibleIdentification(this, other);
    }

    public void checkSamePerson(@NonNull Identification.Provider other) {
        checkSamePerson(other.identification());
    }

    public interface Provider {
        @NonNull Identification identification();

        default void checkSamePerson(@NonNull Identification.Provider other) {
            identification().checkSamePerson(other);
        }

    }
}
