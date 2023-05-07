package perobobbot.twitch.api.eventsub.event;

import io.micronaut.serde.annotation.Serdeable;
import lombok.Value;

@Value
@Serdeable
public class Reward {
    String id;
    String title;
    int cost;
    String prompt;
}
