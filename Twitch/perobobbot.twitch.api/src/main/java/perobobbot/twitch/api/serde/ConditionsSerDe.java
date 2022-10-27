package perobobbot.twitch.api.serde;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ValueNode;
import com.google.common.collect.ImmutableMap;
import fpc.tools.jackson.Registrable;
import fpc.tools.lang.IdentifiedEnumTools;
import lombok.NonNull;
import perobobbot.twitch.api.CriteriaType;
import perobobbot.twitch.api.Conditions;

import java.io.IOException;
import java.util.Map;

public class ConditionsSerDe implements Registrable {

    @Override
    public void register(@NonNull SimpleModule simpleModule) {
        simpleModule.addDeserializer(Conditions.class, new Deserializer());
        simpleModule.addSerializer(Conditions.class, new Serializer());
    }

    public static class Deserializer extends JsonDeserializer<Conditions> {
        @Override
        public Conditions deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
            final var builder = ImmutableMap.<CriteriaType,String>builder();

            final var treeNode = jsonParser.readValueAsTree();
            final var itr = treeNode.fieldNames();
            while (itr.hasNext()) {
                final var fieldName = itr.next();
                final var value = ((ValueNode)treeNode.get(fieldName)).textValue();
                final var conditionType = IdentifiedEnumTools.getEnum(fieldName, CriteriaType.class);

                builder.put(conditionType,value);
            }
            return new Conditions(builder.build());
        }
    }

    public static class Serializer extends JsonSerializer<Conditions> {
        @Override
        public void serialize(Conditions conditions, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeStartObject();
            for (Map.Entry<CriteriaType, String> condition : conditions) {
                jsonGenerator.writeStringField(condition.getKey().getIdentification(),condition.getValue());
            }
            jsonGenerator.writeEndObject();
        }
    }
}
