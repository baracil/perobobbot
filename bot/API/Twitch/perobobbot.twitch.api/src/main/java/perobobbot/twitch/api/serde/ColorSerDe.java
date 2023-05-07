package perobobbot.twitch.api.serde;

import io.micronaut.core.type.Argument;
import io.micronaut.serde.Decoder;
import io.micronaut.serde.Encoder;
import io.micronaut.serde.util.NullableSerde;
import jakarta.inject.Singleton;

import java.awt.*;
import java.io.IOException;

@Singleton
public class ColorSerDe implements NullableSerde<Color> {

    @Override
    public Color deserializeNonNull(Decoder decoder, DecoderContext decoderContext, Argument<? super Color> type) throws IOException {
        return Color.decode(decoder.decodeString());
    }

    @Override
    public void serialize(Encoder encoder, EncoderContext context, Argument<? extends Color> type, Color value) throws IOException {
        final var webFormat = "#%02X%02X%02X".formatted(value.getRed(), value.getGreen(), value.getBlue());
        encoder.encodeString(webFormat);
    }

}
