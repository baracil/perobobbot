package perobobbot.twitch.api.serde;

import org.jetbrains.annotations.Contract;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class TwitchJsonPayloadModifier {

    private static final List<String> SUFFIXES = List.of("id", "login", "name");
    private static final List<String> FULL_SUFFIX = SUFFIXES.stream().map(s -> "_" + s).toList();



    @Contract("null->null; !null -> !null")
    public static Object modify(Object input) {
        if (input == null) {
            return null;
        }
        if (input instanceof Map) {
            final var object = new TwitchJsonPayloadModifier((Map<String,Object>)input).modify();
            for (String key : object.keySet()) {
                final var value = modify(object.get(key));
                object.put(key,value);
            }
            return object;
        } else if (input instanceof Collection<?>) {
            return ((Collection<?>) input).stream().map(TwitchJsonPayloadModifier::modify).collect(Collectors.toList());
        } else if (input.getClass().isArray()) {
            return Arrays.stream(((Object[])input)).map(TwitchJsonPayloadModifier::modify).collect(Collectors.toList());
        }
        return input;
    }

    private final Map<String, Object> input;
    private Set<String> candidates;

    public @NonNull Map<String, Object> modify() {
        findCandidates();
        candidates.forEach(this::rewrite);
        return input;
    }

    private void findCandidates() {
        candidates = input.keySet()
                          .stream()
                          .map(key -> extractPrefix(key, FULL_SUFFIX.get(0)))
                          .filter(Objects::nonNull)
                          .filter(this::haveAllFields)
                          .collect(Collectors.toSet());
    }

    private String extractPrefix(@NonNull String fieldName, String suffix) {
        if (fieldName.endsWith(suffix)) {
            return fieldName.substring(0, fieldName.length() - suffix.length());
        }
        return null;
    }

    private boolean haveAllFields(@NonNull String prefix) {
        return FULL_SUFFIX.stream().map(s -> prefix + s).allMatch(input::containsKey);
    }

    private void rewrite(String candidate) {
        final Map<String,Object> sub = new HashMap<>();
        SUFFIXES.forEach(suffix -> {
            final var removed = input.remove(candidate + "_" + suffix);
            sub.put(suffix,removed);
        });
        final var candidate_key = Objects.requireNonNullElse(extractPrefix(candidate,"_user"),candidate);

        final var allNull = sub.values().stream().allMatch(Objects::isNull);

        if (!allNull) {
            input.put(candidate_key, sub);
        }
    }



}
