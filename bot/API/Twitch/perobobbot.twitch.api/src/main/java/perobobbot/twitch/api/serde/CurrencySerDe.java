package perobobbot.twitch.api.serde;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Currency;

public class CurrencySerDe {

    public static class Serializer extends JsonSerializer<Currency> {
        @Override
        public void serialize(Currency value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            if (value == null) {
                gen.writeNull();
            } else {
                gen.writeString(value.getCurrencyCode());
            }
        }
    }

    public static class Deserializer extends JsonDeserializer<Currency> {
        @Override
        public Currency deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
            final var code = p.getValueAsString();
            if (code == null) {
                return null;
            }
            return Currency.getInstance(code);
        }
    }
}
