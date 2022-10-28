package perobobbot.twitch.web.api.eventsub;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.web.api.Conditions;
import perobobbot.twitch.web.api.SubscriptionType;
import perobobbot.twitch.web.api.serde.ConditionsSerDe;

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
