package perobobbot.twitch.api.channelpoints;

import lombok.Value;

@Value
public class GlobalCooldownSetting {
    boolean enabled;
    int globalCooldownSeconds;
}
