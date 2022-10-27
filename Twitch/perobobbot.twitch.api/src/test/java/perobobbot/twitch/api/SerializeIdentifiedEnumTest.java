package perobobbot.twitch.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import fpc.tools.lang.IdentifiedEnum;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.stream.Stream;

public class SerializeIdentifiedEnumTest extends SerDeBase {


    public static Stream<Arguments> samples() {
        return CLASSES.stream()
               .filter(IdentifiedEnum.class::isAssignableFrom)
                .<Class<? extends IdentifiedEnum>>map(c -> c.asSubclass(IdentifiedEnum.class))
                .map(SerializeIdentifiedEnumTest::prepare);

    }

    @SneakyThrows
    private static Arguments prepare(Class<? extends IdentifiedEnum> clazz) {
        final var value = PODAM_FACTORY.manufacturePojoWithFullData(clazz);
        final var serialized = OBJECT_MAPPER.writeValueAsString(Map.of("value",value));
        final var deserialized = (Map<?,?>)OBJECT_MAPPER.readValue(serialized,constructMapType(String.class));

        return Arguments.of(clazz,value.getIdentification(),deserialized.get("value"));
    }

    @ParameterizedTest
    @MethodSource("samples")
    public void shouldSerialize(@NonNull Class<?> clazz, @NonNull Object expected, @NonNull Object actual) throws JsonProcessingException {
        Assertions.assertEquals(expected,actual);
    }
}
