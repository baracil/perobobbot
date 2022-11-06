package perobobbot.twitch.api.eventsub;

import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;
import lombok.Value;

@Value
@Serdeable
public class EventSubVerification implements EventSubRequest {

    @NonNull String challenge;
    @NonNull TwitchSubscription subscription;

    @Override
    public <T> @NonNull T accept(@NonNull Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
