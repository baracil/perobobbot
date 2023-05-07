package perobobbot.bus.api;

import java.util.Map;
import java.util.Optional;

public interface Message<T> {

    /**
     * @return headers associated with this message
     */
    Map<String,String> getHeaders();

    /**
     * find a header value from its key
     * @param key the header key
     * @return an optional containing the header value associated with the key if one exists, an empty optional otherwise
     */
    Optional<String> findHeader(String key);

    /**
     * @return the payload of this message
     */
    T getPayload();

    <U> Optional<Message<U>> cast(Class<U> type);
}
