package perobobbot.twitch.api;

import lombok.NonNull;
import lombok.Value;
import perobobbot.api.UserInfo;

@Value
public class ChannelInformation implements TwitchApiPayload {

    @NonNull UserInfo broadcaster;
    @NonNull String broadcasterLanguage;
    @NonNull String gameId;
    @NonNull String gameName;
    @NonNull String title;
    int delay;
}
