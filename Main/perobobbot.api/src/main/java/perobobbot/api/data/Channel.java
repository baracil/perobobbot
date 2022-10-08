package perobobbot.api.data;

import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;

@Serdeable
public record Channel(long id, @NonNull Platform platform, @NonNull String name) {

}
