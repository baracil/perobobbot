package perobobbot.twitch.web.api.eventsub.event;

import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.web.api.UserInfo;

@Value
public class Contribution {
    @NonNull UserInfo user;
    @NonNull ContributionType type;
    int total;
}
