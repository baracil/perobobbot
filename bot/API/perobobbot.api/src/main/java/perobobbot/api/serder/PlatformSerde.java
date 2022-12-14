package perobobbot.api.serder;

import io.micronaut.core.type.Argument;
import io.micronaut.serde.Decoder;
import io.micronaut.serde.Encoder;
import io.micronaut.serde.util.NullableSerde;
import jakarta.inject.Singleton;
import lombok.NonNull;
import perobobbot.api.data.Platform;

import java.io.IOException;

@Singleton
public class PlatformSerde implements NullableSerde<Platform> {

    @Override
    public void serialize(Encoder encoder, @NonNull EncoderContext context, @NonNull Argument<? extends Platform> type, Platform value) throws IOException {
        encoder.encodeString(value.name());
    }

    @Override
    public @NonNull Platform deserializeNonNull(Decoder decoder, DecoderContext decoderContext, Argument<? super Platform> type) throws IOException {
        return new Platform(decoder.decodeString());
    }
}
