package perobobbot.bus.api;

import java.util.Map;

public interface MessageBuilder<T> {

    <P> MessageBuilder<P> setPayload(P payload);

    MessageBuilder<T> addHeader(String key, String value);

    MessageBuilder<T> addHeaders(Map<String,String> headers);

    Message<T> build();
}
