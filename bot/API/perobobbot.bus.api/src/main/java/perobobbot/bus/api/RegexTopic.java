package perobobbot.bus.api;

import lombok.NonNull;

import java.util.regex.Pattern;

public record RegexTopic(@NonNull String topicAsString, @NonNull Pattern pattern) implements Topic {
    @Override
    public boolean matches(@NonNull String topicAsString) {
        return pattern.matcher(topicAsString).matches();
    }
}
