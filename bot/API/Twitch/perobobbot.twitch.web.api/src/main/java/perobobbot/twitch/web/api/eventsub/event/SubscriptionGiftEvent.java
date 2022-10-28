package perobobbot.twitch.web.api.eventsub.event;

import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.web.api.UserInfo;
import perobobbot.twitch.web.api.eventsub.Tier;

import java.util.Optional;
import java.util.OptionalInt;

@Value
public class SubscriptionGiftEvent implements BroadcasterProvider, EventSubEvent {

    UserInfo user;
    @NonNull UserInfo broadcaster;
    int total;
    @NonNull Tier tier;
    Integer cumulativeTotal;
    boolean anonymous;

    public @NonNull OptionalInt getCumulativeTotal() {
        return cumulativeTotal==null ? OptionalInt.empty():OptionalInt.of(cumulativeTotal);
    }

    public @NonNull Optional<UserInfo> getUser() {
        return Optional.ofNullable(user);
    }
}
