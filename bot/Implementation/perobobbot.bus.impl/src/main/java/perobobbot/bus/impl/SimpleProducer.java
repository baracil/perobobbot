package perobobbot.bus.impl;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.bus.api.Message;
import perobobbot.bus.api.Producer;
import perobobbot.bus.api.RegularTopic;

@RequiredArgsConstructor
public class SimpleProducer implements Producer {

    private final @NonNull SimpleBus bus;
    private final @NonNull ImmutableSet<RegularTopic> topics;

    @Override
    public void sendMessage(@NonNull Message<?> value) {
        topics.forEach(t -> bus.publishEvent(t,value));
    }

    @Override
    public void send(@NonNull Object payload) {
        send(ImmutableMap.of(),payload);
    }

    @Override
    public void send(@NonNull ImmutableMap<String, String> headers, @NonNull Object payload) {
        sendMessage(new SimpleMessage<>(headers,payload));
    }

    @Override
    public void close()  {}
}
