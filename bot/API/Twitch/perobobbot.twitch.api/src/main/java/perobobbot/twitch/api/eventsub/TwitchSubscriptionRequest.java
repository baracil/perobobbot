package perobobbot.twitch.api.eventsub;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.api.Conditions;
import perobobbot.twitch.api.SubscriptionType;

@Value
@Serdeable
@Builder(builderClassName = "Builder")
public class TwitchSubscriptionRequest {

    @NonNull SubscriptionType type;
    @NonNull String version;
    @NonNull Conditions condition;
    @NonNull TransportRequest transport;

    @JsonProperty("is_batching_enabled")
    Boolean batchingEnabled;

}
