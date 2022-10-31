package perobobbot.twitch.api.eventsub;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;
import lombok.Value;
import perobobbot.api.data.Platform;
import perobobbot.twitch.api.Conditions;
import perobobbot.twitch.api.SubscriptionType;
import perobobbot.twitch.api.Twitch;
import perobobbot.twitch.api.eventsub.event.EventSubEvent;

import java.time.Instant;

@Value
@Introspected
@Serdeable
public class TwitchSubscription {

    @NonNull String id;
    @NonNull SubscriptionType type;
    @NonNull String version;
    @NonNull SubscriptionStatus status;
    int cost;
    @NonNull Conditions condition;
    @NonNull Instant createdAt;
    @NonNull Transport transport;

    @JsonIgnore
    public @NonNull Class<? extends EventSubEvent> getEventType() {
        return type.getEventType();
    }

    @JsonIgnore
    public boolean isValid() {
        return status == SubscriptionStatus.ENABLED;
    }

    @JsonIgnore
    public boolean isFailure() {
        return status.isFailure();
    }

    @JsonIgnore
    public @NonNull Platform getPlatform() {
        return Twitch.PLATFORM;
    }

    @JsonIgnore
    public @NonNull String getSubscriptionType() {
        return type.getIdentification();
    }

    @JsonIgnore
    public @NonNull Conditions getConditions() {
        return condition;
    }

    @JsonIgnore
    public @NonNull String getSubscriptionId() {
        return id;
    }

    @JsonIgnore
    public @NonNull String getCallbackUrl() {
        return transport.getCallback();
    }

}
