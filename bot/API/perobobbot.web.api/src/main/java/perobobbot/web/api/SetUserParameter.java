package perobobbot.web.api;

import io.micronaut.core.annotation.Introspected;
import lombok.NonNull;

@Introspected
public record SetUserParameter(@NonNull String userName) {

}
