package perobobbot.twitch.web.api.eventsub;

import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.web.api.eventsub.event.EventSubEvent;

@Value
public class EventSubNotification implements EventSubRequest {

    @NonNull TwitchSubscription subscription;
    @NonNull EventSubEvent event;

    @Override
    public <T> @NonNull T accept(@NonNull Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
