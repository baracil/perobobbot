package perobobbot.twitch.api.serde;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.core.type.Argument;
import io.micronaut.serde.Decoder;
import io.micronaut.serde.Encoder;
import io.micronaut.serde.Serde;
import jakarta.inject.Singleton;
import perobobbot.twitch.api.Pagination;

import java.io.IOException;

@Singleton
public class PaginationSerDe implements Serde<Pagination> {

    @Override
    public void serialize(Encoder encoder, EncoderContext context, Argument<? extends Pagination> type, Pagination value) throws IOException {
        try (var e = encoder.encodeObject(Argument.mapOf(String.class, String.class))) {
            e.encodeKey("cursor");
            e.encodeString(value.getCursor());
        }
    }

    @Nullable
    @Override
    public Pagination deserialize(Decoder decoder, DecoderContext context, Argument<? super Pagination> type) throws IOException {
        try (var d = decoder.decodeObject()) {
            String key;
            while (null != (key = d.decodeKey())) {
                if ("cursor".equals(key)) {
                    final var cursor = decoder.decodeString();
                    return new Pagination(cursor);
                }
                d.skipValue();
            }
        }
        return null;
    }

}
