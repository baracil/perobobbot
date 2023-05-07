package perobobbot.bus.api;

import java.util.Set;

/**
 * A bus is used have a medium in which messages are producer and from which messages are consumed.
 * Message are emit in topics.
 *
 * <p>
 * A regular topic is of the form <code>namespace:tenant/name</code>. Three value <code>namespace</code>,
 * <code>tenant</code> and <code>name</code> must be made of word characters (letters lowercase and uppercase, numbers and underscore).
 * </p>
 *
 * <p>
 * A regex topic is simple a regular expression that will be used to match a topic. A regex topic if of the form : <code>regex:&lt;pattern&gt;</code>
 * </p>
 */
public interface Bus {

    int VERSION = 1;

    /**
     * Create a producer that send messages to a single topic
     * @param topic the topic on which the producer will send the messages (the topic must be regular)
     * @return the created producer
     */
    Producer createProducer(String topic);

    /**
     * Create a producer that send message to several topics
     * @param topics the topics on which the producer will send the messages (the topics must be regular)
     * @return the created producer
     */
    Producer createProducer(Set<String> topics);

    /**
     * Create a consumer that will listen on a topic for message with payload of the provided type
     * @param topic the topic to listen to (can be regular and regex topics)
     * @param payloadType the type of the payload in which the consumer is interested
     * @return the consumer
     * @param <T> the type of the payload
     */
    <T> Consumer<T> createConsumer(String topic, Class<T> payloadType);

    /**
     * Create a consumer that will listen on a topic for message with payload of the provided type
     * @param topics the topics to listen to (can be regular and regex topics)
     * @param payloadType the type of the payload in which the consumer is interested
     * @return the consumer
     * @param <T> the type of the payload
     */
    <T> Consumer<T> createConsumer(Set<String> topics, Class<T> payloadType);


    <T> Message<T> createSimpleMessage(T payload);

    default void send(String topic, Object payload) {
        try (var producer = this.createProducer(topic)) {
            producer.send(payload);
        }
    }
}
