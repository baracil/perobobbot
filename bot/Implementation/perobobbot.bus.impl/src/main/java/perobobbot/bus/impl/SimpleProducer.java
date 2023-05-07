package perobobbot.bus.impl;

import lombok.RequiredArgsConstructor;
import perobobbot.bus.api.Message;
import perobobbot.bus.api.Producer;
import perobobbot.bus.api.RegularTopic;

import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
public class SimpleProducer implements Producer {

    private final SimpleBus bus;
    private final Set<RegularTopic> topics;

    @Override
    public void sendMessage(Message<?> value) {
        topics.forEach(t -> bus.publishEvent(t,value));
    }

    @Override
    public void send(Object payload) {
        send(Map.of(),payload);
    }

    @Override
    public void send(Map<String, String> headers, Object payload) {
        sendMessage(new SimpleMessage<>(headers,payload));
    }

    @Override
    public void close()  {}
}
