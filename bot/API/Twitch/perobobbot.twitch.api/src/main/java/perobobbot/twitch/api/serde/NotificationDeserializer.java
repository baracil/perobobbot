package perobobbot.twitch.api.serde;

import io.micronaut.core.type.Argument;
import io.micronaut.serde.Decoder;
import io.micronaut.serde.ObjectMapper;
import io.micronaut.serde.util.NullableDeserializer;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import perobobbot.twitch.api.TwitchApiPayload;
import perobobbot.twitch.api.eventsub.EventSubNotification;
import perobobbot.twitch.api.eventsub.TwitchSubscription;
import perobobbot.twitch.api.eventsub.event.EventSubEvent;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Singleton
@RequiredArgsConstructor
public class NotificationDeserializer implements NullableDeserializer<EventSubNotification> {

    private final ObjectMapper objectMapper;

    @Override
    public EventSubNotification deserializeNonNull(Decoder decoder, DecoderContext decoderContext, Argument<? super EventSubNotification> type) throws IOException {
        final Map<?, ?> object = (Map<?, ?>) decoder.decodeArbitrary();

        assert object != null;

        final var subscriptionData = object.get("subscription");
        final var subscriptionNode = objectMapper.writeValueToTree(subscriptionData);
        final var subscription = objectMapper.readValueFromTree(subscriptionNode, TwitchSubscription.class);

        final var eventType = subscription.getEventType();

        final List<EventSubEvent> events;

        final var eventNode = object.get("event");
        final var eventsNode = object.get("events");
        if (eventNode != null) {
            events = List.of(extractSingleEvent(eventNode, eventType));
        } else if (eventsNode != null) {
            events = ((Collection<?>) eventsNode).stream().map(e -> extractSingleEvent(e, eventType)).toList();
        } else {
            events = List.of();
        }
        
        return new EventSubNotification(subscription, events);
    }

    private EventSubEvent extractSingleEvent(Object eventData, Class<? extends EventSubEvent> eventType) {
        final Object effectiveEventData;
        if (TwitchApiPayload.class.isAssignableFrom(eventType)) {
            effectiveEventData = TwitchJsonPayloadModifier.modify(eventData);
        } else {
            effectiveEventData = eventData;

        }
        try {
            final var eventNode = objectMapper.writeValueToTree(effectiveEventData);

            return objectMapper.readValueFromTree(eventNode, eventType);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }


//    @Override
//    public EventSubNotification deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
//        final ObjectNode node = jp.readValueAsTree();
//        final var subscriptionNode = node.get("subscription");
//        final var eventNode = node.get("event");
//
//        final TwitchSubscription subscription = deserializeJsonNode(subscriptionNode,TwitchSubscription.class, jp.getCodec());
//        final EventSubEvent event = deserializeJsonNode(eventNode, subscription.getEventType(), jp.getCodec());
//
//        return new EventSubNotification(subscription,event);
//    }

}
