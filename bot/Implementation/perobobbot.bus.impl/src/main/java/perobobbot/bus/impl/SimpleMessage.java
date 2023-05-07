package perobobbot.bus.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import perobobbot.bus.api.Message;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Getter
public class SimpleMessage<T> implements Message<T> {

    private final Map<String,String> headers;
    private final T payload;

    @Override
    public Optional<String> findHeader(String key) {
        return Optional.ofNullable(headers.get(key));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <U> Optional<Message<U>> cast(Class<U> type) {
        if (type.isInstance(payload)) {
            return Optional.of((Message<U>) this);
        }
        return Optional.empty();
    }
}
