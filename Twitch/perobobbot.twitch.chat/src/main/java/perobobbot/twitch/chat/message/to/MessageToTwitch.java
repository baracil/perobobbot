package perobobbot.twitch.chat.message.to;

import fpc.tools.advanced.chat.Message;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import perobobbot.api.data.Channel;
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

    public static @NonNull Join join(@NonNull Channel channel) {
        return new Join(TwitchChannel.create(channel));
    }

    public static @NonNull Join join(@NonNull String channelName) {
        return new Join(TwitchChannel.create(channelName));
    }

    public static @NonNull Part part(@NonNull Channel channel) {
        return new Part(TwitchChannel.create(channel));
    }

    public static @NonNull Part part(@NonNull String channelName) {
        return new Part(TwitchChannel.create(channelName));
    }

    public static @NonNull PrivMsg privateMsg(@NonNull Channel channel, @NonNull String message) {
        return privateMsg(channel.name(),message);
    }

    public static @NonNull PrivMsg privateMsg(@NonNull String channelName, @NonNull String message) {
        return new PrivMsg(TwitchChannel.create(channelName), i -> message);
    }

}
