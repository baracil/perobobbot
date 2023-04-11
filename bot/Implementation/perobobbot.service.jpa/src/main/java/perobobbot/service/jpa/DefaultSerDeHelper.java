package perobobbot.service.jpa;

import io.micronaut.core.type.Argument;
import io.micronaut.serde.ObjectMapper;
import jakarta.inject.Singleton;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.api.SerDeHelper;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Map;

@Singleton
@RequiredArgsConstructor
public class DefaultSerDeHelper implements SerDeHelper {

    public static final Argument<Map<String, String>> ARGUMENT = Argument.of((Class<Map<String, String>>) ((Class<?>) Map.class), String.class, String.class);


    private final @NonNull ObjectMapper objectMapper;

    @Override
    public @NonNull String serializeMap(@NonNull Map<String, String> map) {
        try {
            return objectMapper.writeValueAsString(map);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public @NonNull Map<String, String> deserializeMap(@NonNull String data) {
        try {
            return objectMapper.readValue(data, ARGUMENT);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
