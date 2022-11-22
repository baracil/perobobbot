package perobobbot.api;

import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;
import perobobbot.api.data.Platform;

@Serdeable
public record Identity(@NonNull Platform platform, @NonNull String userId, @NonNull String userLogin) implements Id {


    @Override
    public <T> @NonNull T accept(@NonNull Visitor<T> visitor) {
        return visitor.visit(this);
    }


    public boolean isSameIdentity(@NonNull Platform platform, @NonNull String userId) {
        return platform.equals(this.platform) && userId.equals(this.userId);
    }


    public void checkSameIdentity(@NonNull Identity other) {
        if (other.equals(this)) {
            return;
        }
        throw new IncompatibleIdentification(this, other);
    }

    public void checkSameIdentity(@NonNull Identity.Provider other) {
        checkSameIdentity(other.identity());
    }


    public interface Provider {
        @NonNull Identity identity();

        default void checkSameIdentity(@NonNull Identity.Provider other) {
            identity().checkSameIdentity(other);
        }

    }
}
