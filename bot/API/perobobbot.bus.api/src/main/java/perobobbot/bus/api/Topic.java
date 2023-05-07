package perobobbot.bus.api;

import perobobbot.bus.api.parser.TopicParser;

public sealed interface Topic permits RegularTopic, RegexTopic {

    /**
     * @param topicAsString a topic in string form
     * @return true if this topic matches the provided topic
     */
    boolean matches(String topicAsString);

    /**
     * @return this topic in string form
     */
    String topicAsString();



    static Topic parse(String topicAsString) {
        return TopicParser.INSTANCE.parse(topicAsString);
    }

    static RegularTopic parseRegular(String topicAsString) {
        return TopicParser.INSTANCE.parseRegular(topicAsString);
    }


}
