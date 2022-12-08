package perobobbot.api.bus;

import lombok.NonNull;

import java.util.Optional;
import java.util.regex.Pattern;

public sealed interface Topic permits Topic.Regular, Topic.Regex {

    boolean matches(@NonNull String topicAsString);
    @NonNull String topicAsString();


    record Regular(@NonNull String topicAsString, @NonNull String namespace, @NonNull String tenant,
                   @NonNull String name) implements Topic {
        @Override
        public boolean matches(@NonNull String topicAsString) {
            return topicAsString.equals(topicAsString);
        }

        public @NonNull String getNamespaceAndTenant() {
            return namespace+":"+tenant;
        }
    }

    record Regex(@NonNull String topicAsString, @NonNull Pattern pattern) implements Topic {
        @Override
        public boolean matches(@NonNull String topicAsString) {
            return pattern.matcher(topicAsString).matches();
        }
    }


    Pattern TOPIC_PATTERN = Pattern.compile("(?<namespace>\\w+):(?<tenant>\\w+)/(?<name>\\$?\\w+)");

    static @NonNull Topic parse(@NonNull String topicAsString) {
        return tryParseRegular(topicAsString)
                .<Topic>map(r -> r)
                .orElseGet(() -> {
            final var pattern = Pattern.compile(topicAsString);
            return new Regex(topicAsString, pattern);
        });
    }

    static @NonNull Optional<Regular> tryParseRegular(@NonNull String topicAsString) {
        final var match = TOPIC_PATTERN.matcher(topicAsString);
        if (match.matches()) {
            return Optional.of(new Regular(topicAsString, match.group("namespace"), match.group("tenant"), match.group("name")));
        }
        return Optional.empty();
    }

    static @NonNull Regular parseRegular(@NonNull String topicAsString) {
        return tryParseRegular(topicAsString).orElseThrow(() -> new IllegalArgumentException("Invalid value '"+topicAsString+"' for regular topic"));
    }


}
