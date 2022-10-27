package perobobbot.twitch.api.channelpoints;

import lombok.Value;

@Value
public class MaxPerUserPerStreamSetting {

    boolean enabled;
    int maxPerUserPerStream;

}
