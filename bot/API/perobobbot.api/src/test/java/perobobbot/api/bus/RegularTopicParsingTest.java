package perobobbot.api.bus;

import lombok.NonNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class RegularTopicParsingTest {


    public static Stream<String> topics() {
        return Stream.of("chat:twitch/$irc");

    }

    @ParameterizedTest
    @MethodSource("topics")
    public void shouldParseWithoutError(@NonNull String topicAsString) {
        Assertions.assertDoesNotThrow(() -> Topic.parseRegular(topicAsString));
    }

}
