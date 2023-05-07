package perobobbot.twitch.api.serde;

import fpc.tools.lang.IdentifiedEnumTools;
import io.micronaut.core.type.Argument;
import io.micronaut.serde.Decoder;
import io.micronaut.serde.Encoder;
import io.micronaut.serde.util.NullableSerde;
import jakarta.inject.Singleton;
import perobobbot.twitch.api.Conditions;
import perobobbot.twitch.api.CriteriaType;

import java.io.IOException;
import java.util.Map;

@Singleton
public class ConditionsSerDe implements NullableSerde<Conditions> {

    public static final Argument<Map<String, String>> MAP_STRING_STRING = Argument.mapOf(String.class, String.class);

    @Override
    public Conditions deserializeNonNull(Decoder decoder, DecoderContext decoderContext, Argument<? super Conditions> type) throws IOException {
        try (var mapDecoder = decoder.decodeObject()) {
            final var builder = Conditions.builder();

            String key;
            while (null != (key = mapDecoder.decodeKey())) {
                String value;
                if (mapDecoder.decodeNull()) {
                    value = null;
                } else {
                    value = mapDecoder.decodeString();
                }
                if (value != null && !value.isBlank()) {
                    builder.put(IdentifiedEnumTools.getEnum(key, CriteriaType.class), value);
                }
            }
            return builder.build();
        }
    }

    @Override
    public void serialize(Encoder encoder, EncoderContext context, Argument<? extends Conditions> type, Conditions value) throws IOException {
        try (var mapEncoder = encoder.encodeObject(MAP_STRING_STRING)) {
            for (Map.Entry<CriteriaType, String> entry : value) {
                mapEncoder.encodeKey(entry.getKey().getIdentification());
                mapEncoder.encodeString(entry.getValue());
            }
        }
    }


}
