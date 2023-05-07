package perobobbot.twitch.api.eventsub;

import lombok.Value;

@Value
public class EventSubRevocation implements EventSubRequest {

    TwitchSubscription subscription;

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
