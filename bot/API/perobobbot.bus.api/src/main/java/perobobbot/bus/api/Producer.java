package perobobbot.bus.api;

import lombok.NonNull;

import java.io.Closeable;
import java.util.Map;

public interface Producer extends Closeable {

    void sendMessage(@NonNull Message<?> value);

    void send(@NonNull Object payload);

    void send(@NonNull Map<String,String> headers, @NonNull Object payload);

    @Override
    void close();
}
