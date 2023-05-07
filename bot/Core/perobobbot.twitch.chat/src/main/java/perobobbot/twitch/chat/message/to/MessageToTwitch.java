package perobobbot.twitch.chat.message.to;

import fpc.tools.advanced.chat.Message;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import perobobbot.twitch.chat.TwitchChannel;

import java.time.Instant;

/**
 * @author Bastien Aracil
 **/
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public abstract class MessageToTwitch implements Message {


    public abstract String commandInPayload();

    @Override
    public String toString() {
        if (commandInPayload().equalsIgnoreCase("PASS")) {
            return "MessageToTwitch{PASS *****}";
        }
        return "MessageToTwitch{" + payload(Instant.EPOCH) + "}";
    }


    public static Join join(String channelName) {
        return new Join(TwitchChannel.create(channelName));
    }

    public static Part part(String channelName) {
        return new Part(TwitchChannel.create(channelName));
    }

    public static PrivMsg privateMsg(String channelName, String message) {
        return new PrivMsg(TwitchChannel.create(channelName), i -> message);
    }

}
