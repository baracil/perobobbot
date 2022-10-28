package perobobbot.twitch.web.api.eventsub.event;

import lombok.Value;

@Value
public class Limit {
    boolean enabled;
    int value;
}
