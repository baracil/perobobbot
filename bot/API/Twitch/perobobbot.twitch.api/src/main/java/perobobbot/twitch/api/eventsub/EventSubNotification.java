package perobobbot.twitch.api.eventsub;

import lombok.Value;
import perobobbot.twitch.api.eventsub.event.EventSubEvent;

import java.util.List;

@Value
public class EventSubNotification implements EventSubRequest {

    TwitchSubscription subscription;
    List<EventSubEvent> events;

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
