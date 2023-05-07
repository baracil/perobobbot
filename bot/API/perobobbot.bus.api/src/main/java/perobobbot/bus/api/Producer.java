package perobobbot.bus.api;

import java.io.Closeable;
import java.util.Map;

public interface Producer extends Closeable {

    void sendMessage(Message<?> value);

    void send(Object payload);

    void send(Map<String,String> headers, Object payload);

    @Override
    void close();
}
