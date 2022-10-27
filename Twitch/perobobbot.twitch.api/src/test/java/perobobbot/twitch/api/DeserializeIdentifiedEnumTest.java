package perobobbot.twitch.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.SimpleType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import fpc.tools.lang.IdentifiedEnum;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class DeserializeIdentifiedEnumTest extends SerDeBase {


    public static Stream<Arguments> samples() {
        return CLASSES.stream()
               .filter(IdentifiedEnum.class::isAssignableFrom)
                .<Class<? extends IdentifiedEnum>>map(c -> c.asSubclass(IdentifiedEnum.class))
                .map(DeserializeIdentifiedEnumTest::prepare);

    }

    @SneakyThrows
    private static Arguments prepare(Class<? extends IdentifiedEnum> clazz) {
        final var value = PODAM_FACTORY.manufacturePojoWithFullData(clazz);
        final var map = Map.of("value",value.getIdentification());
        final var serialized = OBJECT_MAPPER.writeValueAsString(map);
        final var deserialized = (Map<?,?>)OBJECT_MAPPER.readValue(serialized,constructMapType(clazz));
        return Arguments.of(clazz,value,deserialized.get("value"));
    }

    @ParameterizedTest
    @MethodSource("samples")
    public void shouldDeserialize(@NonNull Class<? extends IdentifiedEnum> type, @NonNull IdentifiedEnum expected, @NonNull Object actual) throws JsonProcessingException {
        Assertions.assertEquals(expected,actual);
    }
}
