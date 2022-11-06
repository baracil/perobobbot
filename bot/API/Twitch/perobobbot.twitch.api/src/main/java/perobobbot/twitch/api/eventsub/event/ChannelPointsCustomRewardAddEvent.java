package perobobbot.twitch.api.eventsub.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import io.micronaut.serde.config.naming.SnakeCaseStrategy;
import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.api.Image;
import perobobbot.twitch.api.TwitchApiPayload;
import perobobbot.twitch.api.UserInfo;
import perobobbot.twitch.api.serde.ISOInstantSerde;

import java.time.Instant;
import java.util.Optional;

@Value
@Serdeable(naming = SnakeCaseStrategy.class)
public class ChannelPointsCustomRewardAddEvent implements BroadcasterProvider, EventSubEvent, TwitchApiPayload {

    @NonNull String id;
    @NonNull UserInfo broadcaster;
    @JsonProperty("is_enabled")
    boolean enabled;
    @JsonProperty("is_paused")
    boolean paused;
    @JsonProperty("is_in_stock")
    boolean inStock;
    @NonNull String title;
    int cost;
    @NonNull String prompt;
    @JsonProperty("is_user_input_required")
    boolean userInputRequired;
    boolean shouldRedemptionsSkipRequestQueue;
    @Nullable Limit maxPerStream;
    @Nullable Limit maxPerUserPerStream;
    @NonNull String backgroundColor;
    @Nullable Image image;
    @NonNull Image defaultImage;
    @NonNull GlobalCooldown globalCooldown;
    @Serdeable.Serializable(using = ISOInstantSerde.class)
    @Serdeable.Deserializable(using = ISOInstantSerde.class)
    @Nullable Instant cooldownExpiresAt;
    @Nullable Integer redemptionsRedeemedCurrentStream;


    public @NonNull Optional<Instant> getCooldownExpiresAt() {
        return Optional.ofNullable(cooldownExpiresAt);
    }

    public @NonNull Optional<Integer> getRedemptionsRedeemedCurrentStream() {
        return Optional.ofNullable(redemptionsRedeemedCurrentStream);
    }

    public @NonNull Optional<Limit> getMaxPerStream() {
        return Optional.ofNullable(maxPerStream);
    }

    public @NonNull Optional<Limit> getMaxPerUserPerStream() {
        return Optional.ofNullable(maxPerUserPerStream);
    }

}
