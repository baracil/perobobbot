package perobobbot.api.data;

import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;
import perobobbot.api.data.view.UserIdentity;

import java.util.Objects;

@Serdeable
public record Channel(@NonNull UserIdentity userIdentity, @NonNull String name, boolean mute) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Channel channel = (Channel) o;
        return userIdentity.equals(channel.userIdentity) && name.equals(channel.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userIdentity, name);
    }
}
