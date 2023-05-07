package perobobbot.bus.impl;

import fpc.tools.fp.Tuple2;
import fpc.tools.lang.Subscription;
import io.micronaut.retry.annotation.Fallback;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import perobobbot.api.PerobobbotExecutors;
import perobobbot.api.plugin.PerobobbotService;
import perobobbot.bus.api.*;

import java.time.Duration;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Fallback
@Singleton
@PerobobbotService(serviceType = Bus.class, apiVersion = Bus.VERSION)
public class SimpleBus implements Bus {

    private static final Duration DURATION_WITHOUT_MESSAGE_BEFORE_STOPPING = Duration.ofMinutes(1);


    private final PerobobbotExecutors executors;

    private final Map<String, EventDispatcher> dispatcher = new ConcurrentHashMap<>();
    private final Listeners listeners = new Listeners();

    @Override
    public Producer createProducer(String topic) {
        final var regularTopic = Topic.parseRegular(topic);
        return new SimpleProducer(this, Set.of(regularTopic));
    }

    @Override
    public Producer createProducer(Set<String> topics) {
        final var regularTopics = topics.stream().map(Topic::parseRegular).collect(Collectors.toSet());
        return new SimpleProducer(this, regularTopics);
    }

    @Override
    public <T> Message<T> createSimpleMessage(T payload) {
        return new SimpleMessage<>(Map.of(),payload);
    }

    @Override
    public <T> Consumer<T> createConsumer(String topic, Class<T> payloadType) {
        final var topics = Set.of(Topic.parse(topic));
        return new SimpleConsumer<>(this,executors,topics, payloadType);
    }

    @Override
    public <T> Consumer<T> createConsumer(Set<String> topics, Class<T> payloadType) {
        final var regularTopics = topics.stream().map(Topic::parse).collect(Collectors.toSet());
        return new SimpleConsumer<>(this,executors,regularTopics, payloadType);
    }

    void publishEvent(RegularTopic topic, Message<?> event) {
        final var dispatcherKey = topic.getNamespaceAndTenant();

        this.dispatcher.computeIfAbsent(dispatcherKey, this::createEventDispatch)
                       .onBusEvent(topic, event);
    }

    private EventDispatcher createEventDispatch(String dispatcherKey) {
        final var eventDispatcher = new EventDispatcher(dispatcherKey);
        executors.submit(eventDispatcher);
        return eventDispatcher;
    }


    public <T> Subscription addListener(Topic topic, Class<T> eventType, BusListener<T> listener) {
        return listeners.addListener(topic, eventType, listener);
    }


    @RequiredArgsConstructor
    private class EventDispatcher implements Runnable {

        private final String dispatcherKey;
        private final BlockingDeque<Tuple2<RegularTopic, Message<?>>> pendingData = new LinkedBlockingDeque<>();

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    final var datum = pendingData.poll(DURATION_WITHOUT_MESSAGE_BEFORE_STOPPING.toMillis(), TimeUnit.MILLISECONDS);
                    if (datum == null) {
                        break;
                    }
                    final var topic = datum.v1();
                    final var message = datum.v2();

                    listeners.dispatch(topic, message);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            dispatcher.remove(dispatcherKey);
        }

        public void onBusEvent(RegularTopic topic, Message<?> message) {
            this.pendingData.push(new Tuple2<>(topic, message));
        }
    }
}
