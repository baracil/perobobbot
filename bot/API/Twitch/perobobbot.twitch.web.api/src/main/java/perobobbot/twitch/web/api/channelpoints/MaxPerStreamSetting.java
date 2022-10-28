package perobobbot.twitch.web.api.channelpoints;

import lombok.Value;

@Value
public class MaxPerStreamSetting {

    boolean enabled;
    int maxPerStream;

}
