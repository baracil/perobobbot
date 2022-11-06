package perobobbot.twitch.api;

import io.micronaut.core.annotation.Introspected;
import lombok.NonNull;
import lombok.Value;

@Value
@Introspected
public class Pagination {
    @NonNull String cursor;

}
