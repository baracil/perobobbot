package perobobbot.bus.api;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;

import java.util.Optional;

public interface Message<T> {

    @NonNull ImmutableMap<String,String> getHeaders();

    @NonNull Optional<String> getHeader(@NonNull String key);

    @NonNull T getPayload();

    @NonNull <U> Optional<Message<U>> cast(@NonNull Class<U> type);
}
