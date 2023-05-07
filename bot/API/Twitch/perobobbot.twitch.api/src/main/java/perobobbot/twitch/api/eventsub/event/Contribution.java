package perobobbot.twitch.api.eventsub.event;

import io.micronaut.serde.annotation.Serdeable;
import lombok.Value;
import perobobbot.twitch.api.UserInfo;

@Value
@Serdeable
public class Contribution {
    UserInfo user;
    ContributionType type;
    int total;
}
