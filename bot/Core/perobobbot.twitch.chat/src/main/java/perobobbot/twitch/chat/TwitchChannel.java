package perobobbot.twitch.chat;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Value;

/**
 * @author Bastien Aracil
 **/
@Value
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class TwitchChannel {

    String name;


    public static TwitchChannel create(String channelName) {
        if (channelName.startsWith("#")) {
            return new TwitchChannel(channelName.substring(1).toLowerCase());
        } else {
            return new TwitchChannel(channelName.toLowerCase());
        }
    }

    @Override
    public String toString() {
        return '#'+ name;
    }
}
