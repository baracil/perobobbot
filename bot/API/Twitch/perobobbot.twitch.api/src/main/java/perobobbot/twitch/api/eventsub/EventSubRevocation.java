package perobobbot.twitch.api.eventsub;

import lombok.NonNull;
import lombok.Value;

@Value
public class EventSubRevocation implements EventSubRequest {

    @NonNull TwitchSubscription subscription;

    @Override
    public <T> @NonNull T accept(@NonNull Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
