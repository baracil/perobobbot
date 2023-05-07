package perobobbot.service.jpa;

import io.micronaut.core.type.Argument;
import io.micronaut.serde.ObjectMapper;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import perobobbot.api.SerDeHelper;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Map;

@Singleton
@RequiredArgsConstructor
public class DefaultSerDeHelper implements SerDeHelper {

    public static final Argument<Map<String, String>> ARGUMENT = Argument.of((Class<Map<String, String>>) ((Class<?>) Map.class), String.class, String.class);


    private final ObjectMapper objectMapper;

    @Override
    public String serializeMap(Map<String, String> map) {
        try {
            return objectMapper.writeValueAsString(map);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public Map<String, String> deserializeMap(String data) {
        try {
            return objectMapper.readValue(data, ARGUMENT);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
