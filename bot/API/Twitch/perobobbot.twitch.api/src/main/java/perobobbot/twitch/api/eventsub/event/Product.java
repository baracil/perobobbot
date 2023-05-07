package perobobbot.twitch.api.eventsub.event;

import io.micronaut.serde.annotation.Serdeable;
import io.micronaut.serde.config.naming.SnakeCaseStrategy;
import lombok.Value;

@Value
@Serdeable(naming = SnakeCaseStrategy.class)
public class Product {
    String name;
    int bits;
    String sku;
    boolean inDevelopment;
}
