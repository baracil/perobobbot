package perobobbot.twitch.api.serde;

import io.micronaut.context.annotation.Secondary;
import io.micronaut.core.type.Argument;
import io.micronaut.serde.Decoder;
import io.micronaut.serde.Encoder;
import io.micronaut.serde.util.NullableSerde;
import jakarta.inject.Singleton;

import java.io.IOException;
import java.time.Instant;

@Singleton
@Secondary
public class ISOInstantSerde implements NullableSerde<Instant> {

    @Override
    public void serialize(Encoder encoder, EncoderContext context, Argument<? extends Instant> type, Instant value) throws IOException {
        encoder.encodeString(value.toString());
    }

    @Override
    public Instant deserializeNonNull(Decoder decoder, DecoderContext decoderContext, Argument<? super Instant> type) throws IOException {
        return Instant.parse(decoder.decodeString());
    }
}
