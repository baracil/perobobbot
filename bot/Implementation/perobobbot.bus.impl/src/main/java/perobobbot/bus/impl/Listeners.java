package perobobbot.bus.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import fpc.tools.lang.ListTool;
import fpc.tools.lang.Subscription;
import fpc.tools.lang.ThrowableTool;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import perobobbot.bus.api.Message;
import perobobbot.bus.api.RegularTopic;
import perobobbot.bus.api.Topic;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

@Slf4j
public class Listeners {

    private ImmutableMap<String, ImmutableList<Listener<?>>> listeners = ImmutableMap.of();

    public void dispatch(@NonNull RegularTopic topic, @NonNull Message<?> message) {
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
                () -> Optional.of(ImmutableList.of(data)));

        return () -> remove(topic.topicAsString(), data.getId());
    }

    @Synchronized
    private void remove(@NonNull String topicAsString, long id) {
        final Predicate<Listener<?>> dataMatcher = d -> d.getId() == id;
        this.listeners = update(topicAsString, list -> ListTool.removeOnceFrom(list).apply(dataMatcher), Optional::empty);
    }


    private ImmutableMap<String, ImmutableList<Listener<?>>> update(@NonNull String topicAsString,
                                                                    @NonNull UnaryOperator<ImmutableList<Listener<?>>> updater,
                                                                    @NonNull Supplier<Optional<ImmutableList<Listener<?>>>> ifAbsent) {


        final var builder = ImmutableMap.<String, ImmutableList<Listener<?>>>builder();
        var updated = false;
        for (Map.Entry<String, ImmutableList<Listener<?>>> entry : listeners.entrySet()) {
            final ImmutableList<Listener<?>> list;

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


        return builder.build();

    }


    @RequiredArgsConstructor
    private static class Listener<T> {
        private static final AtomicLong ID_GENERATOR = new AtomicLong(0);
        @Getter
        long id = ID_GENERATOR.incrementAndGet();
        private final @NonNull Topic topic;
        private final @NonNull Class<T> eventType;
        private final @NonNull BusListener<T> listener;

        public boolean isConcerned(@NonNull RegularTopic topic) {
            return this.topic.matches(topic.topicAsString());
        }

        public void dispatch(@NonNull Message<?> message) {
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
