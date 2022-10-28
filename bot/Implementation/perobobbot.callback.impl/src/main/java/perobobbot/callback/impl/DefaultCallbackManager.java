package perobobbot.callback.impl;

import com.google.common.collect.ImmutableMap;
import fpc.tools.lang.MapTool;
import fpc.tools.lang.Subscription;
import jakarta.inject.Singleton;
import lombok.NonNull;
import lombok.Synchronized;
import perobobbot.callback.api.Callback;
import perobobbot.callback.api.CallbackIdIsAlreadyUsed;
import perobobbot.callback.api.CallbackManager;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

@Singleton
public class DefaultCallbackManager implements CallbackManager {

    private ImmutableMap<String, Callback> callbacks = ImmutableMap.of();

    @Override
    public @NonNull Optional<Callback> findCallback(@NonNull String id) {
        return Optional.ofNullable(callbacks.get(id));
    }

    @Override
    @Synchronized
    public @NonNull Subscription addCallback(@NonNull String id, @NonNull Callback callback) {
        if (callbacks.containsKey(id)) {
            throw new CallbackIdIsAlreadyUsed(id);
        }
        this.callbacks = ImmutableMap.<String, Callback>builder()
                                     .putAll(this.callbacks)
                                     .put(id, callback)
                                     .build();
        return () -> removeCallback(id);
    }

    @Synchronized
    private void removeCallback(String id) {
        if (!callbacks.containsKey(id)) {
            return;
        }
        callbacks = callbacks.keySet()
                             .stream()
                             .filter(Predicate.not(id::equals))
                             .collect(MapTool.collector(Function.identity(), callbacks::get));
    }
}
