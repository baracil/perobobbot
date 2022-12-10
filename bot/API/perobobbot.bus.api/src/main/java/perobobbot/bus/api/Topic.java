package perobobbot.bus.api;

import lombok.NonNull;
import perobobbot.bus.api.parser.TopicParser;

public sealed interface Topic permits RegularTopic, RegexTopic {

    /**
     * @param topicAsString a topic in string form
     * @return true if this topic matches the provided topic
     */
    boolean matches(@NonNull String topicAsString);

    /**
     * @return this topic in string form
     */
    @NonNull String topicAsString();



    static @NonNull Topic parse(@NonNull String topicAsString) {
        return TopicParser.INSTANCE.parse(topicAsString);
    }

    static @NonNull RegularTopic parseRegular(@NonNull String topicAsString) {
        return TopicParser.INSTANCE.parseRegular(topicAsString);
    }


}
