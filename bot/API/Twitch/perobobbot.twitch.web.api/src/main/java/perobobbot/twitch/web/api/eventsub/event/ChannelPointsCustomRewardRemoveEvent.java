package perobobbot.twitch.web.api.eventsub.event;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.web.api.Image;
import perobobbot.twitch.web.api.UserInfo;

import java.time.Instant;
import java.util.Optional;

@Value
public class ChannelPointsCustomRewardRemoveEvent implements BroadcasterProvider, EventSubEvent {

    @NonNull String id;
    @NonNull UserInfo broadcaster;
    boolean enabled;
    boolean paused;
    boolean inStock;
    @NonNull String title;
    int cost;
    @NonNull String prompt;
    boolean userInputRequired;
    @Getter(AccessLevel.NONE)
    boolean shouldRedemptionsSkipRequestQueue;
    Limit maxPerStream;
    Limit maxPerUserPerStream;
    @NonNull String backgroundColor;
    Image image;
    @NonNull Image defaultImage;
    @NonNull GlobalCooldown globalCooldown;
    Instant cooldownExpiresAt;
    Integer redemptionsRedeemedCurrentStream;


    public @NonNull Optional<Instant> getCooldownExpiresAt() {
        return Optional.ofNullable(cooldownExpiresAt);
    }

    public @NonNull Optional<Integer> getRedemptionsRedeemedCurrentStream() {
        return Optional.ofNullable(redemptionsRedeemedCurrentStream);
    }

    public boolean shouldRedemptionsSkipRequestQueue() {
        return shouldRedemptionsSkipRequestQueue;
    }

    public @NonNull Optional<Limit> getMaxPerStream() {
        return Optional.ofNullable(maxPerStream);
    }

    public @NonNull Optional<Limit> getMaxPerUserPerStream() {
        return Optional.ofNullable(maxPerUserPerStream);
    }
}
