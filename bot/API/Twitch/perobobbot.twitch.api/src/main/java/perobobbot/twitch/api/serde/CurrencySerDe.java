package perobobbot.twitch.api.serde;

import io.micronaut.core.type.Argument;
import io.micronaut.serde.Decoder;
import io.micronaut.serde.Encoder;
import io.micronaut.serde.util.NullableSerde;
import jakarta.inject.Singleton;
import lombok.NonNull;

import java.io.IOException;
import java.util.Currency;

@Singleton
public class CurrencySerDe implements NullableSerde<Currency> {

    @Override
    public void serialize(Encoder encoder, @NonNull EncoderContext context, @NonNull Argument<? extends Currency> type, Currency value) throws IOException {
        encoder.encodeString(value.getCurrencyCode());
    }

    @Override
    public @NonNull Currency deserializeNonNull(Decoder decoder, DecoderContext decoderContext, Argument<? super Currency> type) throws IOException {
        final var currencyCode = decoder.decodeString();
        return Currency.getInstance(currencyCode);
    }
}
