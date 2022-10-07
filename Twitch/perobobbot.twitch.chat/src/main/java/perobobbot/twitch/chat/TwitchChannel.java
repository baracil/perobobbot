package perobobbot.twitch.chat;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import perobobbot.api.data.Channel;

/**
 * @author Bastien Aracil
 **/
@Value
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class TwitchChannel {

    @NonNull String name;


    @NonNull
    public static TwitchChannel create(@NonNull String channelName) {
        if (channelName.startsWith("#")) {
            return new TwitchChannel(channelName.substring(1).toLowerCase());
        } else {
            return new TwitchChannel(channelName.toLowerCase());
        }
    }

    @NonNull
    public static TwitchChannel create(@NonNull Channel channel) {
        return create(channel.name());
    }

    @Override
    public String toString() {
        return '#'+ name;
    }
}
