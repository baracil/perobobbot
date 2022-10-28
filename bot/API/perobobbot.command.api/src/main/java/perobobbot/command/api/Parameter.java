package perobobbot.command.api;

import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;

@Serdeable
public record Parameter(@NonNull String name, boolean optional) {

        }
