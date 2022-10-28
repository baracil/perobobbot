package perobobbot.twitch.web.api.eventsub.event;

import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.web.api.UserInfo;

@Value
public class TopPredictor {
    @NonNull UserInfo user;
    int channelPointsWon;
    int channelPointsUsed;
}
