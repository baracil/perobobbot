package perobobbot.twitch.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import fpc.tools.lang.IdentifiedEnum;
import lombok.NonNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SerDeTest extends SerDeBase {


    public static Stream<Arguments> samples() {
        return IntStream.range(0, 10).mapToObj(i -> CLASSES.stream()
                                                           .filter(Predicate.not(c -> c.isAssignableFrom(IdentifiedEnum.class)))
                                                           .map(Arguments::of))
                        .flatMap(s -> s);
    }


    @ParameterizedTest
    @MethodSource("samples")
    public void name(@NonNull Class<?> clazz) throws JsonProcessingException {
        final Object value = PODAM_FACTORY.manufacturePojoWithFullData(clazz);
        final var serialized = OBJECT_MAPPER.writeValueAsString(value);
        final var deserialized = OBJECT_MAPPER.readValue(serialized, clazz);

        Assertions.assertEquals(value, deserialized);
    }
}
