package perobobbot.twitch.api.channelpoints;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.SuperBuilder;

@Value
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateCustomRewardParameter extends CustomRewardParameterBase{

    String title;
    int cost;

}
