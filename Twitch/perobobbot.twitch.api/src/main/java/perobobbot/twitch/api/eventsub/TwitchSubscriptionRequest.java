package perobobbot.twitch.api.eventsub;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.api.Conditions;
import perobobbot.twitch.api.serde.ConditionsSerDe;
import perobobbot.twitch.api.SubscriptionType;

@Value
@Builder(builderClassName = "Builder")
public class TwitchSubscriptionRequest {

    @NonNull SubscriptionType type;
    @NonNull String version;
    @JsonSerialize(using = ConditionsSerDe.Serializer.class)
    @NonNull Conditions condition;
    @NonNull TransportRequest transport;
    @JsonProperty("is_batching_enabled")
    Boolean batchingEnabled;

}
