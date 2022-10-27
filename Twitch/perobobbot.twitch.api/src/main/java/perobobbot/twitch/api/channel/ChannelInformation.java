package perobobbot.twitch.api.channel;

import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.api.TwitchApiPayload;
import perobobbot.twitch.api.UserInfo;

@Value
public class ChannelInformation implements TwitchApiPayload {

    @NonNull UserInfo broadcaster;
    @NonNull String broadcasterLanguage;
    @NonNull String gameId;
    @NonNull String gameName;
    @NonNull String title;
    int delay;
}
