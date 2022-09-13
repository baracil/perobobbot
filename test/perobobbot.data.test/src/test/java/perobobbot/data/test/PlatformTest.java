package perobobbot.data.test;

import lombok.NonNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import perobobbot.data.io.Platform;

import java.util.stream.Stream;

public class PlatformTest {


    public static Stream<Platform> platforms() {
        return Stream.of("twitch","Twitch","TWiTch","TWITCH")
                .map(Platform::new);
    }

    @ParameterizedTest
    @MethodSource("platforms")
    public void shouldMatchToTwitch(@NonNull Platform platform) {
        Assertions.assertEquals(new Platform("TWITCH"), platform);
    }
}
