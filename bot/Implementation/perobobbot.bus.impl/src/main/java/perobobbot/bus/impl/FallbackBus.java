package perobobbot.bus.impl;

import fpc.tools.fp.Tuple2;
import fpc.tools.lang.Subscription;
import io.micronaut.retry.annotation.Fallback;
import jakarta.inject.Singleton;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.api.Event;
import perobobbot.api.PerobobbotExecutors;
import perobobbot.api.plugin.PerobobbotService;
import perobobbot.api.plugin.PerobobbotServices;
import perobobbot.bus.api.*;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Fallback
@Singleton
@PerobobbotServices({
        @PerobobbotService(serviceType = Bus.class, apiVersion = Bus.VERSION),
        @PerobobbotService(serviceType = EventDispatcher.class, apiVersion = EventDispatcher.VERSION)
})
public class FallbackBus implements Bus {

    private final @NonNull PerobobbotExecutors executors;

    private static final Duration DURATION_WITHOUT_MESSAGE_BEFORE_STOPPING = Duration.ofMinutes(1);

    private final Map<String, EventDispatcher> dispatcher = new ConcurrentHashMap<>();
    private final Listeners listeners = new Listeners();


    public void publishEvent(@NonNull String topicAsString, @NonNull Event event) {
        final var topic = Topic.parseRegular(topicAsString);
        final var dispatcherKey = topic.getNamespaceAndTenant();

        this.dispatcher.computeIfAbsent(dispatcherKey, this::createEventDispatch)
                       .onBusEvent(topic, event);
    }

    private EventDispatcher createEventDispatch(String dispatcherKey) {
        final var eventDispatcher = new EventDispatcher(dispatcherKey);
        executors.submit(eventDispatcher);
        return eventDispatcher;
    }


    @Override
    public <T> Subscription addListener(@NonNull String topicAsString, @NonNull Class<T> eventType, @NonNull BusListener<? super T> listener) {
        final var topic = Topic.parse(topicAsString);
        return listeners.addListener(topic, eventType, listener);
    }


    @RequiredArgsConstructor
    private class EventDispatcher implements Runnable {

        private final @NonNull String dispatcherKey;
        private final BlockingDeque<Tuple2<RegularTopic, Event>> pendingData = new LinkedBlockingDeque<>();

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    final var datum = pendingData.poll(DURATION_WITHOUT_MESSAGE_BEFORE_STOPPING.toMillis(), TimeUnit.MILLISECONDS);
                    if (datum == null) {
                        break;
                    }
                    final var topic = datum.v1();
                    final var event = datum.v2();

                    listeners.dispatch(topic, event);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            dispatcher.remove(dispatcherKey);
        }

        public void onBusEvent(@NonNull RegularTopic topic, @NonNull Event event) {
            this.pendingData.push(new Tuple2<>(topic, event));
        }
    }
}
