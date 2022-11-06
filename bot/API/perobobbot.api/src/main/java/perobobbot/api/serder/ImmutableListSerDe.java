package perobobbot.api.serder;

import com.google.common.collect.ImmutableList;
import io.micronaut.core.annotation.Order;
import io.micronaut.core.type.Argument;
import io.micronaut.serde.Decoder;
import io.micronaut.serde.util.NullableDeserializer;
import jakarta.inject.Singleton;
import lombok.NonNull;

import java.io.IOException;

@Singleton
@Order(2000)
public class ImmutableListSerDe<E> implements NullableDeserializer<ImmutableList<E>> {

    @Override
    public @NonNull ImmutableList<E> deserializeNonNull(Decoder decoder, DecoderContext context, Argument<? super ImmutableList<E>> type) throws IOException {
        final var elementType = (Argument<E>) type.getTypeParameters()[0];
        final var valueDeser = context.findDeserializer(elementType).createSpecific(context,elementType);
        final var builder = ImmutableList.<E>builder();
        try (var d = decoder.decodeArray()) {
            while (d.hasNextArrayValue()) {
                final var e = valueDeser.deserialize(d, context, elementType);
                builder.add(e);
            }
        }
        return builder.build();
    }
}
