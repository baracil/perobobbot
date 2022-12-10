package perobobbot.bus.api.parser;

import lombok.NonNull;
import perobobbot.bus.api.RegexTopic;
import perobobbot.bus.api.RegularTopic;
import perobobbot.bus.api.Topic;

import java.util.Optional;
import java.util.regex.Pattern;

public enum TopicParser {
    INSTANCE;

    private static final Pattern TOPIC_PATTERN = Pattern.compile("(?<namespace>\\w+):(?<tenant>\\w+)/(?<name>\\$?\\w+)");


    public @NonNull Topic parse(@NonNull String topicAsString) {
        return tryParseRegular(topicAsString)
                .<Topic>map(r -> r)
                .orElseGet(() -> {
                    final var pattern = Pattern.compile(topicAsString);
                    return new RegexTopic(topicAsString, pattern);
                });
    }

    public @NonNull Optional<RegularTopic> tryParseRegular(@NonNull String topicAsString) {
        final var match = TOPIC_PATTERN.matcher(topicAsString);
        if (match.matches()) {
            return Optional.of(new RegularTopic(topicAsString, match.group("namespace"), match.group("tenant"), match.group("name")));
        }
        return Optional.empty();
    }

    public @NonNull RegularTopic parseRegular(@NonNull String topicAsString) {
        return tryParseRegular(topicAsString).orElseThrow(() -> new IllegalArgumentException("Invalid value '"+topicAsString+"' for regular topic"));
    }

}
