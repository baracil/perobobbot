package perobobbot.bus.api.parser;

import perobobbot.bus.api.RegexTopic;
import perobobbot.bus.api.RegularTopic;
import perobobbot.bus.api.Topic;

import java.util.Optional;
import java.util.regex.Pattern;

public enum TopicParser {
    INSTANCE;

    public static final String REGEXP_PREFIX = "regexp:";

    private static final Pattern TOPIC_PATTERN = Pattern.compile("(?<namespace>\\w+):(?<tenant>\\w+)/(?<name>\\$?\\w+)");


    public Topic parse(String topicAsString) {
        if (topicAsString.startsWith(REGEXP_PREFIX)) {
            final var pattern = Pattern.compile(topicAsString.substring(REGEXP_PREFIX.length()));
            return new RegexTopic(topicAsString, pattern);
        }
        return parseRegular(topicAsString);
    }

    public Optional<RegularTopic> tryParseRegular(String topicAsString) {
        final var match = TOPIC_PATTERN.matcher(topicAsString);
        if (match.matches()) {
            return Optional.of(new RegularTopic(topicAsString, match.group("namespace"), match.group("tenant"), match.group("name")));
        }
        return Optional.empty();
    }

    public RegularTopic parseRegular(String topicAsString) {
        final var match = TOPIC_PATTERN.matcher(topicAsString);
        if (!match.matches()) {
            return tryParseRegular(topicAsString).orElseThrow(() -> new IllegalArgumentException("Invalid value '"+topicAsString+"' for regular topic"));
        }
        final var namespace = match.group("namespace");
        final var tenant = match.group("tenant");
        final var name = match.group("name");
        return new RegularTopic(topicAsString,namespace,tenant,name);
    }

}
