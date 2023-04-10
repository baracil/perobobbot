package perobobbot.bus.api;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;

import java.io.Closeable;

public interface Producer extends Closeable {

    void sendMessage(@NonNull Message<?> value);

    void send(@NonNull Object payload);

    void send(@NonNull ImmutableMap<String,String> headers, @NonNull Object payload);

    @Override
    void close();
}
