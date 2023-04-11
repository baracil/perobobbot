package perobobbot.twitch.api;

import fpc.tools.lang.IdentifiedEnumTools;
import fpc.tools.lang.MapTool;
import io.micronaut.core.annotation.Introspected;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

import java.util.*;

@Getter
@Value
@Introspected
public class Conditions implements Iterable<Map.Entry<CriteriaType, String>> {

    @NonNull Map<CriteriaType, String> values;

    public @NonNull Optional<String> findConditionValue(@NonNull CriteriaType criteriaType) {
        return Optional.ofNullable(values.get(criteriaType));
    }

    public @NonNull Map<String, String> toMap() {
        return values.entrySet().stream().collect(MapTool.collector(e -> e.getKey().getIdentification(), Map.Entry::getValue));
    }

    @Override
    public Iterator<Map.Entry<CriteriaType, String>> iterator() {
        return values.entrySet().iterator();
    }

    public static @NonNull Conditions singleCondition(@NonNull CriteriaType criteriaType, @NonNull String value) {
        return new Conditions(Map.of(criteriaType,value));
    }

    public static @NonNull Conditions fromMap(@NonNull Map<String, String> conditions) {
        final var values = conditions.entrySet()
                                     .stream()
                                     .collect(MapTool.collector(e -> IdentifiedEnumTools.getEnum(e.getKey(), CriteriaType.class), Map.Entry::getValue));
        return new Conditions(values);
    }


    public static @NonNull Builder builder() {
        return new Builder();
    }


    public static class Builder {
        private final Map<CriteriaType,String> values = new HashMap<>();

        public Builder put(@NonNull CriteriaType criteriaType, String value) {
            if (value != null) {
                values.put(criteriaType,value);
            }
            return this;
        }

        public @NonNull Conditions build() {
            return new Conditions(Collections.unmodifiableMap(values));
        }

    }
}
