package perobobbot.twitch.api.channelpoints;

import lombok.Value;

@Value
public class BasicReward {

    String id;
    String title;
    String prompt;
    int cost;

}
