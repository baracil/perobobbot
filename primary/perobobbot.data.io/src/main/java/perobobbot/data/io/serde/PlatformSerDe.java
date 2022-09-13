package perobobbot.data.io.serde;

import io.micronaut.core.type.Argument;
import io.micronaut.serde.Decoder;
import io.micronaut.serde.Encoder;
import io.micronaut.serde.Serde;
import jakarta.inject.Singleton;
import lombok.NonNull;
import perobobbot.data.io.Platform;

import java.io.IOException;

@Singleton
public class PlatformSerDe implements Serde<Platform> {

    @Override
    public Platform deserialize(
            @NonNull Decoder decoder,
            @NonNull DecoderContext context,
            @NonNull Argument<? super Platform> type) throws IOException {
        final var platformAsString = decoder.decodeString();
        return new Platform(platformAsString);
    }

    @Override
    public void serialize(
            @NonNull Encoder encoder,
            @NonNull EncoderContext context,
            @NonNull Argument<? extends Platform> type, @NonNull Platform value) throws IOException {
        encoder.encodeString(value.name());
    }
}
