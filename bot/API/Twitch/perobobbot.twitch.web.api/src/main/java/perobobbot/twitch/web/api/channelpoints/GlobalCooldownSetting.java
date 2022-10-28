package perobobbot.twitch.web.api.channelpoints;

import lombok.Value;

@Value
public class GlobalCooldownSetting {
    boolean enabled;
    int globalCooldownSeconds;
}
