package perobobbot.twitch.api.eventsub;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.api.eventsub.event.EventSubEvent;

@Value
public class EventSubNotification implements EventSubRequest {

    @NonNull TwitchSubscription subscription;
    @NonNull ImmutableList<EventSubEvent> events;

    @Override
    public <T> @NonNull T accept(@NonNull Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
