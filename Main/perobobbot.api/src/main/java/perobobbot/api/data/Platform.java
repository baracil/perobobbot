package perobobbot.api.data;

import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;

@Serdeable
public record Platform(@NonNull String name) {

    public Platform(@NonNull String name) {
        this.name = name.toUpperCase();
    }

    @Override
    public String toString() {
        return "Platform{" + name + '}';
    }
}
