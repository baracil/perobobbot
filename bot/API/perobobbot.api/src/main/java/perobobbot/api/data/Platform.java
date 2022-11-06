package perobobbot.api.data;

import io.micronaut.core.annotation.Introspected;
import lombok.NonNull;

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
