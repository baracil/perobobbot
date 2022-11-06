package test.perobobbot.twitch.api;

import io.micronaut.serde.ObjectMapper;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import lombok.NonNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import perobobbot.twitch.api.eventsub.EventSubNotification;
import perobobbot.twitch.api.eventsub.EventSubVerification;
import perobobbot.twitch.api.eventsub.TwitchSubscriptionData;
import perobobbot.twitch.oauth.dto.TwitchToken;

import java.net.URL;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@MicronautTest(startApplication = false)
public class DataDeserTest {

    protected @Inject ObjectMapper objectMapper;

    public static @NonNull Stream<Arguments> samples() {
        return Stream.of(
                             EventSubNotification.class, TwitchSubscriptionData.class,
                             TwitchToken.class,
                             EventSubVerification.class)
                     .flatMap(DataDeserTest::getResources);
    }

    private static Stream<Arguments> getResources(Class<?> cls) {
        final var simpleName = cls.getSimpleName();
        return IntStream.iterate(1, i -> i + 1)
                        .mapToObj(i -> "%s_%02d.json".formatted(simpleName, i))
                        .map(DataDeserTest.class::getResource)
                        .takeWhile(Objects::nonNull)
                        .map(url -> Arguments.of(cls, url));
    }

    @ParameterizedTest
    @MethodSource("samples")
    public void testDeserialization(@NonNull Class<?> type, @NonNull URL url) {
        Assertions.assertDoesNotThrow(() -> {
            try (var is = url.openStream()) {
                final var value = objectMapper.readValue(is, type);
            }
        });
    }
}