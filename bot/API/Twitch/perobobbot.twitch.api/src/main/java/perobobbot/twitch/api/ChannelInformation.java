package perobobbot.twitch.api;

import lombok.Value;
import perobobbot.api.UserInfo;

@Value
public class ChannelInformation implements TwitchApiPayload {

    UserInfo broadcaster;
    String broadcasterLanguage;
    String gameId;
    String gameName;
    String title;
    int delay;
}
