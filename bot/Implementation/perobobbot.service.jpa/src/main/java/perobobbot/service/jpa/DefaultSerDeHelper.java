package perobobbot.service.jpa;

import com.google.common.collect.ImmutableMap;
import io.micronaut.core.type.Argument;
import io.micronaut.serde.ObjectMapper;
import jakarta.inject.Singleton;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.api.SerDeHelper;

import java.io.IOException;
import java.io.UncheckedIOException;

@Singleton
@RequiredArgsConstructor
public class DefaultSerDeHelper implements SerDeHelper {

    public static final Argument<ImmutableMap<String, String>> ARGUMENT = Argument.of((Class<ImmutableMap<String, String>>) ((Class) ImmutableMap.class), String.class, String.class);


    private final @NonNull ObjectMapper objectMapper;

    @Override
    public @NonNull String serializeMap(@NonNull ImmutableMap<String, String> map) {
        try {
            return objectMapper.writeValueAsString(map);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public @NonNull ImmutableMap<String, String> deserializeMap(@NonNull String data) {
        try {
            return objectMapper.readValue(data, ARGUMENT);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
