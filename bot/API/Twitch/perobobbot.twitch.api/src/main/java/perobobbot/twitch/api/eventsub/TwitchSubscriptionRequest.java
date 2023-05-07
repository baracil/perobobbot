package perobobbot.twitch.api.eventsub;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Builder;
import lombok.Value;
import perobobbot.twitch.api.Conditions;
import perobobbot.twitch.api.SubscriptionType;

@Value
@Serdeable
@Builder(builderClassName = "Builder")
public class TwitchSubscriptionRequest {

    SubscriptionType type;
    String version;
    Conditions condition;
    TransportRequest transport;

    @JsonProperty("is_batching_enabled")
    Boolean batchingEnabled;

}
