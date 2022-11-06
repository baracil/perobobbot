package perobobbot.twitch.api.channelpoints;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import lombok.experimental.NonFinal;
import lombok.experimental.SuperBuilder;

import java.awt.*;

@Value
@NonFinal
@SuperBuilder
public class CustomRewardParameterBase {

    String prompt;
    @JsonProperty("is_enabled")
    Boolean enabled;//true
    Color backgroundColor;
    @JsonProperty("is_user_input_required")
    Boolean userInputRequired;//false
    @JsonProperty("is_max_per_stream_enabled")
    Boolean maxPerStreamEnabled; //false
    Integer maxPerStream;

    @JsonProperty("is_max_per_user_per_stream_enabled")
    Boolean maxPerUserPerStreamEnabled; //false
    Integer maxPerUserPerStream;

    @JsonProperty("is_global_cooldown_enabled")
    Boolean globalCooldownEnabled;
    Integer globalCooldownSeconds;

    Boolean shouldRedemptionsSkipRequestQueue;


}
