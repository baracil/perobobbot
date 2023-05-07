package perobobbot.twitch.api.eventsub.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import io.micronaut.serde.config.naming.SnakeCaseStrategy;
import lombok.Value;
import perobobbot.twitch.api.Image;
import perobobbot.twitch.api.TwitchApiPayload;
import perobobbot.twitch.api.UserInfo;
import perobobbot.twitch.api.serde.ISOInstantSerde;

import java.time.Instant;
import java.util.Optional;

@Value
@Serdeable(naming = SnakeCaseStrategy.class)
public class ChannelPointsCustomRewardRemoveEvent implements BroadcasterProvider, EventSubEvent, TwitchApiPayload {

    String id;
    UserInfo broadcaster;
    @JsonProperty("is_enabled")
    boolean enabled;
    @JsonProperty("is_paused")
    boolean paused;
    @JsonProperty("is_in_stock")
    boolean inStock;
    String title;
    int cost;
    String prompt;
    @JsonProperty("is_user_input_required")
    boolean userInputRequired;
    boolean shouldRedemptionsSkipRequestQueue;
    @Nullable Limit maxPerStream;
    @Nullable Limit maxPerUserPerStream;
    String backgroundColor;
    @Nullable Image image;
    Image defaultImage;
    GlobalCooldown globalCooldown;

    @Serdeable.Serializable(using = ISOInstantSerde.class)
    @Serdeable.Deserializable(using = ISOInstantSerde.class)
    @Nullable Instant cooldownExpiresAt;

    @Serdeable.Serializable(using = ISOInstantSerde.class)
    @Serdeable.Deserializable(using = ISOInstantSerde.class)
    @Nullable Integer redemptionsRedeemedCurrentStream;


    public Optional<Instant> getCooldownExpiresAt() {
        return Optional.ofNullable(cooldownExpiresAt);
    }

    public Optional<Integer> getRedemptionsRedeemedCurrentStream() {
        return Optional.ofNullable(redemptionsRedeemedCurrentStream);
    }

    public Optional<Limit> getMaxPerStream() {
        return Optional.ofNullable(maxPerStream);
    }

    public Optional<Limit> getMaxPerUserPerStream() {
        return Optional.ofNullable(maxPerUserPerStream);
    }
}
