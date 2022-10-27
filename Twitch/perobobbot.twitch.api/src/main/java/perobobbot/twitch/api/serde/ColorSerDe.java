package perobobbot.twitch.api.serde;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import fpc.tools.jackson.Registrable;
import lombok.NonNull;

import java.awt.*;
import java.io.IOException;

public class ColorSerDe implements Registrable {

    @Override
    public void register(@NonNull SimpleModule simpleModule) {
        simpleModule.addSerializer(Color.class, new Serializer());
        simpleModule.addDeserializer(Color.class, new Deserializer());
    }

    public static class Serializer extends JsonSerializer<Color> {
        public void serialize(Color value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            final var webFormat = "#%02X%02X%02X".formatted(value.getRed(), value.getGreen(), value.getBlue());
            gen.writeString(webFormat);
        }
    }

    public static class Deserializer extends JsonDeserializer<Color> {
        @Override
        public Color deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
            return Color.decode(p.getValueAsString());
        }
    }
}
