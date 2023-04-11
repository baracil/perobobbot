package perobobbot.twitch.api.eventsub;

import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.api.eventsub.event.EventSubEvent;

import java.util.List;

@Value
public class EventSubNotification implements EventSubRequest {

    @NonNull TwitchSubscription subscription;
    @NonNull List<EventSubEvent> events;

    @Override
    public <T> @NonNull T accept(@NonNull Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
