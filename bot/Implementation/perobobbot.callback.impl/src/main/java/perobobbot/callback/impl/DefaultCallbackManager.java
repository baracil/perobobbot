package perobobbot.callback.impl;

import fpc.tools.lang.MapTool;
import fpc.tools.lang.Subscription;
import jakarta.inject.Singleton;
import lombok.Synchronized;
import perobobbot.callback.api.Callback;
import perobobbot.callback.api.CallbackIdIsAlreadyUsed;
import perobobbot.callback.api.CallbackManager;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

@Singleton
public class DefaultCallbackManager implements CallbackManager {

    private Map<String, Callback> callbacks = Map.of();

    @Override
    public Optional<Callback> findCallback(String id) {
        return Optional.ofNullable(callbacks.get(id));
    }

    @Override
    @Synchronized
    public Subscription addCallback(String id, Callback callback) {
        if (callbacks.containsKey(id)) {
            throw new CallbackIdIsAlreadyUsed(id);
        }
        this.callbacks = MapTool.addOneEntry(this.callbacks,id,callback);
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
