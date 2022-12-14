package perobobbot.twitch.api.eventsub.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import io.micronaut.serde.config.naming.SnakeCaseStrategy;
import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.api.TwitchApiPayload;
import perobobbot.twitch.api.UserInfo;
import perobobbot.twitch.api.eventsub.Tier;

import java.util.Optional;
import java.util.OptionalInt;

@Value
@Serdeable(naming = SnakeCaseStrategy.class)
public class SubscriptionGiftEvent implements BroadcasterProvider, EventSubEvent, TwitchApiPayload {

    @Nullable UserInfo user;
    @NonNull UserInfo broadcaster;
    int total;
    @NonNull Tier tier;
    @Nullable Integer cumulativeTotal;
    @JsonProperty("is_anonymous")
    boolean anonymous;

    public @NonNull OptionalInt getCumulativeTotal() {
        return cumulativeTotal==null ? OptionalInt.empty():OptionalInt.of(cumulativeTotal);
    }

    public @NonNull Optional<UserInfo> getUser() {
        return Optional.ofNullable(user);
    }
}
