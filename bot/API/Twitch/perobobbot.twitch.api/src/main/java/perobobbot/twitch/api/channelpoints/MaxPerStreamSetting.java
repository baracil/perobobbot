package perobobbot.twitch.api.channelpoints;

import lombok.Value;

@Value
public class MaxPerStreamSetting {

    boolean enabled;
    int maxPerStream;

}
