package perobobbot.twitch.api.serde;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fpc.tools.jackson.Registrable;
import lombok.NonNull;
import perobobbot.twitch.api.Pagination;

import java.io.IOException;

public class PaginationSerDe implements Registrable {

    @Override
    public void register(@NonNull SimpleModule simpleModule) {
        simpleModule.addDeserializer(Pagination.class, new Deserializer());
        simpleModule.addSerializer(Pagination.class, new Serializer());
    }

    public static class Deserializer extends JsonDeserializer<Pagination> {
        @Override
        public Pagination deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            final ObjectNode node = p.readValueAsTree();
            final var cursor = node.get("cursor");
            if (cursor == null || cursor.isNull()) {
                return null;
            }
            return new Pagination(cursor.asText());
        }
    }

    public static class Serializer extends JsonSerializer<Pagination> {

        @Override
        public void serialize(Pagination value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            if (value == null) {
                gen.writeNull();
            } else {
                gen.writeString(value.getCursor());
            }
        }

    }
}
