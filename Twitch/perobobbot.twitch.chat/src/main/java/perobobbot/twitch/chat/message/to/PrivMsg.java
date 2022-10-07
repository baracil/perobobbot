package perobobbot.twitch.chat.message.to;

import fpc.tools.fp.Function1;
import lombok.NonNull;
import perobobbot.twitch.chat.TwitchChannel;
import perobobbot.twitch.chat.message.IRCCommand;

import java.time.Instant;

/**
 * @author Bastien Aracil
 **/
public class PrivMsg extends CommandToTwitch {

    @NonNull
    private final TwitchChannel channel;

    @NonNull
    private final Function1<? super Instant, ? extends String> messageBuilder;

    public PrivMsg(
            @NonNull TwitchChannel channel,
            @NonNull Function1<? super Instant, ? extends String> messageBuilder) {
        super(IRCCommand.PRIVMSG);
        this.channel = channel;
        this.messageBuilder = messageBuilder;
    }

    @Override
    public @NonNull String payload(@NonNull Instant dispatchInstant) {
        final String message = messageBuilder.apply(dispatchInstant);
        PrivMsgValidator.validate(message);
        return "PRIVMSG #"+channel.getName()+" :"+message;
    }
}
