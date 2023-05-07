package perobobbot.bus.impl;

import fpc.tools.lang.ListTool;
import fpc.tools.lang.MapTool;
import fpc.tools.lang.Subscription;
import fpc.tools.lang.ThrowableTool;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import perobobbot.bus.api.Message;
import perobobbot.bus.api.RegularTopic;
import perobobbot.bus.api.Topic;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

@Slf4j
public class Listeners {

    private Map<String, List<Listener<?>>> listeners = Map.of();

    public void dispatch(RegularTopic topic, Message<?> message) {
        listeners.values()
                 .stream()
                 .flatMap(Collection::stream)
                 .filter(listener -> listener.isConcerned(topic))
                 .forEach(listener -> listener.dispatch(message));

    }


    @Synchronized
    public <T> Subscription addListener(Topic topic, Class<T> eventType, BusListener<T> listener) {
        final var data = new Listener<>(topic, eventType, listener);
        this.listeners = update(
                topic.topicAsString(),
                list -> ListTool.addFirst(data, list),
                () -> Optional.of(List.of(data)));

        return () -> remove(topic.topicAsString(), data.getId());
    }

    @Synchronized
    private void remove(String topicAsString, long id) {
        final Predicate<Listener<?>> dataMatcher = d -> d.getId() == id;
        this.listeners = update(topicAsString, list -> ListTool.removeOnceFrom(list).apply(dataMatcher), Optional::empty);
    }


    private Map<String, List<Listener<?>>> update(String topicAsString,
                                                                    UnaryOperator<List<Listener<?>>> updater,
                                                                    Supplier<Optional<List<Listener<?>>>> ifAbsent) {


        final var builder = MapTool.<String,List<Listener<?>>>hashMap();
        var updated = false;
        for (Map.Entry<String, List<Listener<?>>> entry : listeners.entrySet()) {
            final List<Listener<?>> list;

            if (entry.getKey().equals(topicAsString)) {
                list = updater.apply(entry.getValue());
                updated = true;
            } else {
                list = entry.getValue();
            }

            if (!list.isEmpty()) {
                builder.put(entry.getKey(), list);
            }
        }

        if (!updated) {
            ifAbsent.get().ifPresent(list -> builder.put(topicAsString,list));
        }


        return builder;

    }


    @RequiredArgsConstructor
    private static class Listener<T> {
        private static final AtomicLong ID_GENERATOR = new AtomicLong(0);
        @Getter
        long id = ID_GENERATOR.incrementAndGet();
        private final Topic topic;
        private final Class<T> eventType;
        private final BusListener<T> listener;

        public boolean isConcerned(RegularTopic topic) {
            return this.topic.matches(topic.topicAsString());
        }

        public void dispatch(Message<?> message) {
            message.cast(eventType).ifPresent(msg -> {
                try {
                    listener.onBusEvent(msg);
                } catch (Exception e) {
                    ThrowableTool.interruptIfCausedByInterruption(e);
                    LOG.warn("Fail to warn listener {} on topic {}", this.listener, topic);
                    throw new RuntimeException(e);
                }
            });
        }
    }

}
