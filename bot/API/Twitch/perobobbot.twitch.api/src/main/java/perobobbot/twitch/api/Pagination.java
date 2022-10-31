package perobobbot.twitch.api;

import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;
import lombok.Value;

@Value
@Serdeable
public class Pagination {
    @NonNull String cursor;

}
