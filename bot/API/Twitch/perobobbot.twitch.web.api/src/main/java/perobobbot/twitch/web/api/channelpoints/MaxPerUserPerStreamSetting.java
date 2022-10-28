package perobobbot.twitch.web.api.channelpoints;

import lombok.Value;

@Value
public class MaxPerUserPerStreamSetting {

    boolean enabled;
    int maxPerUserPerStream;

}
