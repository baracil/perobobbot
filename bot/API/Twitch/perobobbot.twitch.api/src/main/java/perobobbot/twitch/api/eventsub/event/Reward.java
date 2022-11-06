package perobobbot.twitch.api.eventsub.event;

import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;
import lombok.Value;

@Value
@Serdeable
public class Reward {
    @NonNull String id;
    @NonNull String title;
    int cost;
    @NonNull String prompt;
}
