package perobobbot.service.jpa;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import jakarta.inject.Singleton;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.api.SerDeHelper;

import java.io.UncheckedIOException;

@Singleton
@RequiredArgsConstructor
public class DefaultSerDeHelper implements SerDeHelper {

    private final @NonNull TypeReference<ImmutableMap<String,String>> MAP_REFERENCE = new TypeReference<>() {};

    private final @NonNull ObjectMapper objectMapper;

    @Override
    public @NonNull String serializeMap(@NonNull ImmutableMap<String, String> map) {
        try {
            return objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public @NonNull ImmutableMap<String, String> deserializeMap(@NonNull String data) {
        try {
            return objectMapper.readValue(data, MAP_REFERENCE);
        } catch (JsonProcessingException e) {
            throw new UncheckedIOException(e);
        }
    }
}
