package perobobbot.twitch.api.eventsub;

import io.micronaut.serde.annotation.Serdeable;
import lombok.Value;

@Value
@Serdeable
public class EventSubVerification implements EventSubRequest {

    String challenge;
    TwitchSubscription subscription;

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
