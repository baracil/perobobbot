package perobobbot.launcher.holder;

import com.google.common.collect.ImmutableMap;
import io.micronaut.core.type.MutableHeaders;
import lombok.NonNull;
import perobobbot.api.oauth.HeaderHolder;

import java.util.LinkedList;
import java.util.Queue;

public class SimpleHeaderHolder implements HeaderHolder {

    private final Queue<ImmutableMap<String, String>> queue = new LinkedList<>();

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    @Override
    public void push(@NonNull ImmutableMap<String, String> data) {
        queue.add(data);
    }

    @Override
    public void pop() {
        queue.poll();
    }

    @Override
    public void setHeaders(@NonNull MutableHeaders headers) {
        queue.stream()
             .flatMap(m -> m.entrySet().stream())
             .forEach(e -> headers.add(e.getKey(), e.getValue()));
    }
}
