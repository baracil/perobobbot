package perobobbot.twitch.api.eventsub.event;

import lombok.Value;

@Value
public class Limit {
    boolean enabled;
    int value;
}
