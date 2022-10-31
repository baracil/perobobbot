package perobobbot.twitch.api.eventsub;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Value;
import perobobbot.twitch.api.serde.CurrencySerDe;

import java.util.Currency;

@Value
public class Amount {
    int value;
    int decimalPlaces;
    @JsonSerialize(using = CurrencySerDe.Serializer.class)
    @JsonDeserialize(using = CurrencySerDe.Deserializer.class)
    Currency currency;
}
