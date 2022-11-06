package test.perobobbot.twitch.api;

import io.micronaut.serde.ObjectMapper;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import perobobbot.twitch.api.eventsub.TransportMethod;
import perobobbot.twitch.api.eventsub.TransportRequest;

import java.io.IOException;

@MicronautTest
public class TransformRequestSerializerTest {

    @Inject
    ObjectMapper objectMapper;


    @Test
    public void name() throws IOException {
        final var t = new TransportRequest(TransportMethod.WEBHOOK,"http://url", "asd");
        final var st = objectMapper.writeValueAsString(t);

        System.out.println(st);

    }
}
