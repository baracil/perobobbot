package perobobbot.api.serder;

import com.google.common.collect.ImmutableMap;
import io.micronaut.core.annotation.Order;
import io.micronaut.core.convert.exceptions.ConversionErrorException;
import io.micronaut.core.type.Argument;
import io.micronaut.serde.Decoder;
import io.micronaut.serde.Deserializer;
import io.micronaut.serde.exceptions.SerdeException;
import jakarta.inject.Singleton;

import java.io.IOException;
import java.util.Map;

@Singleton
@Order(1003)
public class ImmutableMapSerDe<K, V> implements Deserializer<ImmutableMap<K, V>> {

    @Override
    public ImmutableMap<K, V> deserialize(Decoder decoder, DecoderContext context, Argument<? super ImmutableMap<K, V>> type) throws IOException {
        final var typeParameters = type.getTypeParameters();
        if (decoder.decodeNull()) {
            return null;
        }
        if (typeParameters.length == 2) {
            final var keyType = (Argument<K>) typeParameters[0];
            final var valueType = (Argument<V>) typeParameters[1];
            final var valueDeser = context.findDeserializer(valueType).createSpecific(context,valueType);

            final var builder = ImmutableMap.<K, V>builder();
            try (var objectDecoder = decoder.decodeObject(type)) {
                String key;
                var conversionService = context.getConversionService();
                while (null != (key = objectDecoder.decodeKey())) {
                    final K typedKey;
                    if (keyType.isInstance(key)) {
                        typedKey = (K) key;
                    } else {
                        try {
                            typedKey = conversionService.convertRequired(key, keyType);
                        } catch (ConversionErrorException e) {
                            throw new SerdeException("Error converting Map key [" + key + "] to target type [" + keyType + "]: " + e.getMessage(), e);
                        }
                    }
                    builder.put(typedKey, valueDeser.deserialize(objectDecoder, context, valueType));
                }
            }
            return builder.build();
        } else {
            final Object o = decoder.decodeArbitrary();
            if (type.isInstance(o)) {
                return (ImmutableMap<K, V>) o;
            } else if (o instanceof Map) {
                return ImmutableMap.copyOf((Map) o);
            } else {
                throw new SerdeException("Cannot deserialize map of type [" + type + "] from value: " + o);
            }

        }

    }
}
