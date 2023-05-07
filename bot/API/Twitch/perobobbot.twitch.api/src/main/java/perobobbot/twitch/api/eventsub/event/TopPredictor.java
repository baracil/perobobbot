package perobobbot.twitch.api.eventsub.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Value;
import perobobbot.twitch.api.UserInfo;

@Value
@Serdeable
public class TopPredictor  {
    UserInfo user;
    @JsonProperty("channel_points_won")
    int channelPointsWon;
    @JsonProperty("channel_points_used")
    int channelPointsUsed;
}
