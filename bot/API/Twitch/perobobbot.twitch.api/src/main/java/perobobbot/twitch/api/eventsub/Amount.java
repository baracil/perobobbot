package perobobbot.twitch.api.eventsub;

import io.micronaut.serde.annotation.Serdeable;
import io.micronaut.serde.config.naming.SnakeCaseStrategy;
import lombok.Value;

import java.util.Currency;

@Value
@Serdeable(naming = SnakeCaseStrategy.class)
public class Amount {
    int value;
    int decimalPlaces;
   Currency currency;
}
