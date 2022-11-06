package perobobbot.twitch.api.eventsub.event;

import io.micronaut.serde.annotation.Serdeable;
import io.micronaut.serde.config.naming.SnakeCaseStrategy;
import lombok.NonNull;
import lombok.Value;

@Value
@Serdeable(naming = SnakeCaseStrategy.class)
public class Product {
    @NonNull String name;
    int bits;
    @NonNull String sku;
    boolean inDevelopment;
}
