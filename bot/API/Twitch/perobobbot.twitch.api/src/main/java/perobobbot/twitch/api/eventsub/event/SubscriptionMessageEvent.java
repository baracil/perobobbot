package perobobbot.twitch.api.eventsub.event;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import io.micronaut.serde.config.naming.SnakeCaseStrategy;
import lombok.Value;
import perobobbot.twitch.api.TwitchApiPayload;
import perobobbot.twitch.api.UserInfo;
import perobobbot.twitch.api.eventsub.Tier;

import java.util.Optional;

@Value
@Serdeable(naming = SnakeCaseStrategy.class)
public class SubscriptionMessageEvent implements BroadcasterProvider, EventSubEvent, TwitchApiPayload {

    UserInfo user;
    UserInfo broadcaster;
    Tier tier;
    Message message;
    int cumulativeTotal;
    @Nullable Integer streakMonths;
    int durationMonths;

    public Optional<Integer> getStreakMonths() {
        return Optional.ofNullable(streakMonths);
    }
}
