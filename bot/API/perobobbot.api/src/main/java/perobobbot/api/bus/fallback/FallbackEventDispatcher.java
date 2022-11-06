package perobobbot.api.bus.fallback;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import fpc.tools.lang.ListTool;
import fpc.tools.lang.Subscription;
import fpc.tools.lang.ThrowableTool;
import io.micronaut.scheduling.annotation.Async;
import jakarta.inject.Singleton;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import perobobbot.api.bus.BusEventListener;
import perobobbot.api.bus.BusListener;
import perobobbot.api.bus.Event;
import perobobbot.api.bus.EventDispatcher;
import perobobbot.api.plugin.PerobobbotService;

import java.util.Map;

@Singleton
@PerobobbotService(serviceType = EventDispatcher.class, apiVersion = EventDispatcher.VERSION)
@Slf4j
public class FallbackEventDispatcher implements EventDispatcher {

    private ImmutableMap<Class<?>, TypedListeners<?>> listenersPerType = ImmutableMap.of();

    @BusEventListener
    @Async
    public void onBusEvent(@NonNull Event event) {
        listenersPerType.values().forEach(t -> t.dispatchEvent(event));
    }

    @Override
    @Synchronized
    public <T> Subscription addListener(@NonNull Class<T> eventType, BusListener<? super T> listener) {
        final var builder = ImmutableMap.<Class<?>, TypedListeners<?>>builder();

        boolean added = false;
        for (Map.Entry<Class<?>, TypedListeners<?>> entry : listenersPerType.entrySet()) {
            final var type = entry.getKey();
            final var listeners = entry.getValue();

            if (eventType.equals(type)) {
                added = true;
                ((TypedListeners<T>) listeners).addListener(listener);
            }
        }
        if (!added) {
            builder.put(eventType, TypedListeners.initial(eventType, listener));
        }
        listenersPerType = builder.build();
        return () -> removeListener(eventType, listener);

    }

    @Synchronized
    private <T> void removeListener(@NonNull Class<?> eventType, BusListener<? super T> listener) {
        final TypedListeners<?> listeners = listenersPerType.get(eventType);
        if (listeners != null) {
            listeners.removeListener(listener);
        }
    }

    @RequiredArgsConstructor
    private static class TypedListeners<T> {

        public static <T> TypedListeners<T> initial(@NonNull Class<T> type, @NonNull BusListener<? super T> listener) {
            return new TypedListeners<>(type, ImmutableList.of(listener));
        }

        private @NonNull Class<T> type;
        private @NonNull ImmutableList<BusListener<? super T>> listeners;

        public void dispatchEvent(@NonNull Event event) {
            if (!type.isInstance(event)) {
                return;
            }
            final var t = type.cast(event);
            for (BusListener<? super T> listener : listeners) {
                try {
                    listener.onBusEvent(t);
                } catch (Throwable e) {
                    ThrowableTool.isCausedByInterruption(e);
                    LOG.warn("Fail to call listener '{}' :", listener, e);
                }
            }
        }

        public void removeListener(BusListener<?> listener) {
            listeners = ListTool.removeOnceFrom(listeners).apply(l -> l == listener);
        }

        public void addListener(BusListener<? super T> listener) {
            listeners = ListTool.addSorted(
                    listeners,
                    (l1, l2) -> l2.priority() - l1.priority(),
                    listener);
        }
    }
}
