package perobobbot.twitch.eventsub;

import io.micronaut.serde.ObjectMapper;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import perobobbot.twitch.api.eventsub.EventSubNotification;
import perobobbot.twitch.api.eventsub.EventSubRequest;
import perobobbot.twitch.api.eventsub.EventSubVerification;
import perobobbot.twitch.api.eventsub.Markers;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class TwitchRequestDeserializer {

    public static @NonNull Optional<EventSubRequest> deserialize(@NonNull ObjectMapper objectMapper,
                                                                 @NonNull String type,
                                                                 byte @NonNull [] content) {
        return new TwitchRequestDeserializer(objectMapper, type, content).deserialize();
    }

    public static @NonNull Optional<EventSubRequest> deserialize(@NonNull ObjectMapper objectMapper,
                                                                 @NonNull String type,
                                                                 @NonNull String content) {
        return new TwitchRequestDeserializer(objectMapper, type, content.getBytes(StandardCharsets.UTF_8)).deserialize();
    }

    private static final Map<String, Class<? extends EventSubRequest>> CLASS_PER_TYPE = Map.of(
            "notification", EventSubNotification.class,
            "webhook_callback_verification", EventSubVerification.class,
            "revocation", EventSubVerification.class
    );

    private final @NonNull ObjectMapper objectMapper;
    private final @NonNull String type;
    private final byte @NonNull [] content;

    private Optional<EventSubRequest> deserialize() {
        final var clazz = CLASS_PER_TYPE.get(type);
        if (clazz == null) {
            LOG.error(Markers.EVENT_SUB_MARKER, "Unknown EventSub Twitch request type {}", type);
            return Optional.empty();
        }
        try {
            return Optional.ofNullable(objectMapper.readValue(content, clazz));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
