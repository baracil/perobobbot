package perobobbot.bus.api;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;

public interface Bus {

    int VERSION = 1;

    @NonNull Producer createProducer(@NonNull String topic);

    @NonNull Producer createProducer(@NonNull ImmutableSet<String> topics);

    @NonNull <T> Consumer<T> createConsumer(@NonNull String topic, @NonNull Class<T> eventType);

    @NonNull <T> Consumer<T> createConsumer(@NonNull ImmutableSet<String> topics, @NonNull Class<T> eventType);

    @NonNull <T> Message<T> createSimpleMessage(T payload);

    default void send(@NonNull String topic, @NonNull Object payload) {
        try (var producer = this.createProducer(topic)) {
            producer.send(payload);
        }
    }
}
