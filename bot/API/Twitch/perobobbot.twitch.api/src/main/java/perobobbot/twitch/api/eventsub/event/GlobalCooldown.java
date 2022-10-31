package perobobbot.twitch.api.eventsub.event;

import lombok.Value;

@Value
public class GlobalCooldown {
    boolean enabled;
    int seconds;
}
