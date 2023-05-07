package perobobbot.twitch.api.eventsub;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Value;
import perobobbot.api.data.Platform;
import perobobbot.twitch.api.Conditions;
import perobobbot.twitch.api.SubscriptionType;
import perobobbot.twitch.api.Twitch;
import perobobbot.twitch.api.eventsub.event.EventSubEvent;
import perobobbot.twitch.api.serde.ISOInstantSerde;
import perobobbot.twitch.api.serde.MySerdeable;

import java.time.Instant;

@Value
@Serdeable
public class TwitchSubscription {

    String id;
    SubscriptionType type;
    String version;
    SubscriptionStatus status;
    int cost;
    Conditions condition;


    @MySerdeable(property = "created_at",serwith = ISOInstantSerde.class, deserwith = ISOInstantSerde.class)
    Instant createdAt;

    Transport transport;

    @JsonIgnore
    public Class<? extends EventSubEvent> getEventType() {
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
    public Platform getPlatform() {
        return Twitch.PLATFORM;
    }

    @JsonIgnore
    public String getSubscriptionType() {
        return type.getIdentification();
    }

    @JsonIgnore
    public Conditions getConditions() {
        return condition;
    }

    @JsonIgnore
    public String getSubscriptionId() {
        return id;
    }

    @JsonIgnore
    public String getCallbackUrl() {
        return transport.getCallback();
    }

}
