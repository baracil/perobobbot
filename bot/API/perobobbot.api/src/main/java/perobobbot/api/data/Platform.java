package perobobbot.api.data;

import io.micronaut.core.annotation.Introspected;

@Introspected
public record Platform(String name) {

    public Platform(String name) {
        this.name = name.toUpperCase();
    }

    @Override
    public String toString() {
        return "Platform{" + name + '}';
    }
}
