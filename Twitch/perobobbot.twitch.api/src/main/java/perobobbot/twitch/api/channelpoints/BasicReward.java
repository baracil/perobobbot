package perobobbot.twitch.api.channelpoints;

import lombok.NonNull;
import lombok.Value;

@Value
public class BasicReward {

    @NonNull String id;
    @NonNull String title;
    @NonNull String prompt;
    int cost;

}
