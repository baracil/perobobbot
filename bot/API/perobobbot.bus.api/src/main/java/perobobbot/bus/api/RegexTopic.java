package perobobbot.bus.api;

import java.util.regex.Pattern;

public record RegexTopic(String topicAsString, Pattern pattern) implements Topic {
    @Override
    public boolean matches(String topicAsString) {
        return pattern.matcher(topicAsString).matches();
    }
}
