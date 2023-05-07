package perobobbot.api.bus;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import perobobbot.bus.api.Topic;

import java.util.stream.Stream;

public class RegularTopicParsingTest {


    public static Stream<String> topics() {
        return Stream.of("chat:twitch/$irc");

    }

    @ParameterizedTest
    @MethodSource("topics")
    public void shouldParseWithoutError(String topicAsString) {
        Assertions.assertDoesNotThrow(() -> Topic.parseRegular(topicAsString));
    }

}
