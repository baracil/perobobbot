package perobobbot.web.api;

import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;

@Serdeable
public record SetUserParameter(@NonNull String userName) {

}
