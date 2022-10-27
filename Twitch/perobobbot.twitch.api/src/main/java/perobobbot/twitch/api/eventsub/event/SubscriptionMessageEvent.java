package perobobbot.twitch.api.eventsub.event;

import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.api.UserInfo;
import perobobbot.twitch.api.eventsub.Tier;

import java.util.OptionalInt;

@Value
public class SubscriptionMessageEvent implements BroadcasterProvider, EventSubEvent {

    @NonNull UserInfo user;
    @NonNull UserInfo broadcaster;
    @NonNull Tier tier;
    @NonNull Message message;
    int cumulativeTotal;
    Integer streakMonths;
    int durationMonths;

    public @NonNull OptionalInt getStreakMonths() {
        return streakMonths == null ? OptionalInt.empty():OptionalInt.of(streakMonths);
    }
}
