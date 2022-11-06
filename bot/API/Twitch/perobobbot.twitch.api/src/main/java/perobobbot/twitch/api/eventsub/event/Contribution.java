package perobobbot.twitch.api.eventsub.event;

import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.api.UserInfo;

@Value
@Serdeable
public class Contribution {
    @NonNull UserInfo user;
    @NonNull ContributionType type;
    int total;
}
