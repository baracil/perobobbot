package perobobbot.bus.api;

import lombok.NonNull;

import java.util.Map;
import java.util.Optional;

public interface Message<T> {

    @NonNull Map<String,String> getHeaders();

    @NonNull Optional<String> getHeader(@NonNull String key);

    @NonNull T getPayload();

    @NonNull <U> Optional<Message<U>> cast(@NonNull Class<U> type);
}
