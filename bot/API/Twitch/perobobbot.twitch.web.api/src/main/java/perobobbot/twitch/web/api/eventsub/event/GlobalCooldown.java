package perobobbot.twitch.web.api.eventsub.event;

import lombok.Value;

@Value
public class GlobalCooldown {
    boolean enabled;
    int seconds;
}
