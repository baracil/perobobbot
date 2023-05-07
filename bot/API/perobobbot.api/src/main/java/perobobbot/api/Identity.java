package perobobbot.api;

import io.micronaut.serde.annotation.Serdeable;
import perobobbot.api.data.Platform;

@Serdeable
public record Identity(Platform platform, String userId) implements Id {


    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }


    public boolean isSameIdentity(Platform platform, String userId) {
        return platform.equals(this.platform) && userId.equals(this.userId);
    }


    public void checkSameIdentity(Identity other) {
        if (other.equals(this)) {
            return;
        }
        throw new IncompatibleIdentification(this, other);
    }

    public void checkSameIdentity(Identity.Provider other) {
        checkSameIdentity(other.identity());
    }


    public interface Provider {
        Identity identity();

        default void checkSameIdentity(Identity.Provider other) {
            identity().checkSameIdentity(other);
        }

    }
}
