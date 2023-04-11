package perobobbot.bus.impl;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.bus.api.Message;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Getter
public class SimpleMessage<T> implements Message<T> {

    private final @NonNull Map<String,String> headers;
    private final @NonNull T payload;

    @Override
    public @NonNull Optional<String> getHeader(@NonNull String key) {
        return Optional.ofNullable(headers.get(key));
    }

    @SuppressWarnings("unchecked")
    @Override
    public @NonNull <U> Optional<Message<U>> cast(@NonNull Class<U> type) {
        if (type.isInstance(payload)) {
            return Optional.of((Message<U>) this);
        }
        return Optional.empty();
    }
}
