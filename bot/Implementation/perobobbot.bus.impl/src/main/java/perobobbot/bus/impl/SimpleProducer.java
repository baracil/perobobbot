package perobobbot.bus.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.bus.api.Message;
import perobobbot.bus.api.Producer;
import perobobbot.bus.api.RegularTopic;

import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
public class SimpleProducer implements Producer {

    private final @NonNull SimpleBus bus;
    private final @NonNull Set<RegularTopic> topics;

    @Override
    public void sendMessage(@NonNull Message<?> value) {
        topics.forEach(t -> bus.publishEvent(t,value));
    }

    @Override
    public void send(@NonNull Object payload) {
        send(Map.of(),payload);
    }

    @Override
    public void send(@NonNull Map<String, String> headers, @NonNull Object payload) {
        sendMessage(new SimpleMessage<>(headers,payload));
    }

    @Override
    public void close()  {}
}
