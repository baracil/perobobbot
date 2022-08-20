package perobobbot.server.io.view;

import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;

@Serdeable
public record CustomerView(@NonNull Long id, @NonNull String firstName, @NonNull String lastName) {

}
