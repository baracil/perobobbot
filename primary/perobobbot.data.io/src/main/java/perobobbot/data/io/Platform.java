package perobobbot.data.io;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;

@Serdeable
@Introspected
public record Platform(@NonNull String name) {

    public Platform(@NonNull String name) {
        this.name = name.toUpperCase();
    }

    @Override
    public String toString() {
        return "Platform{" + name + '}';
    }
}
