package perobobbot.twitch.api;

import io.micronaut.core.annotation.Introspected;
import lombok.Value;

@Value
@Introspected
public class Pagination {
    String cursor;

}
