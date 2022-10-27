package perobobbot.twitch.api.eventsub;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NonNull;
import lombok.Value;
import perobobbot.api.data.Platform;
import perobobbot.twitch.api.Conditions;
import perobobbot.twitch.api.Twitch;
import perobobbot.twitch.api.eventsub.event.EventSubEvent;
import perobobbot.twitch.api.eventsub.subscription.SubscriptionIdentity;
import perobobbot.twitch.api.SubscriptionType;

import java.time.Instant;

@Value
public class TwitchSubscription implements SubscriptionIdentity {

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

    @Override
    @JsonIgnore
    public @NonNull Platform getPlatform() {
        return Twitch.PLATFORM;
    }

    @Override
    @JsonIgnore
    public @NonNull String getSubscriptionType() {
        return type.getIdentification();
    }

    @Override
    @JsonIgnore
    public @NonNull Conditions getConditions() {
        return condition;
    }

    @Override
    @JsonIgnore
    public @NonNull String getSubscriptionId() {
        return id;
    }

    @Override
    @JsonIgnore
    public @NonNull String getCallbackUrl() {
        return transport.getCallback();
    }
}
