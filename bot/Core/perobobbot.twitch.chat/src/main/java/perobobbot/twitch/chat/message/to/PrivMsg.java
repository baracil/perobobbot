package perobobbot.twitch.chat.message.to;

import fpc.tools.fp.Function1;
import perobobbot.twitch.chat.TwitchChannel;
import perobobbot.twitch.chat.message.IRCCommand;

import java.time.Instant;

/**
 * @author Bastien Aracil
 **/
public class PrivMsg extends CommandToTwitch {

    private final TwitchChannel channel;

    private final Function1<? super Instant, ? extends String> messageBuilder;

    public PrivMsg(
            TwitchChannel channel,
            Function1<? super Instant, ? extends String> messageBuilder) {
        super(IRCCommand.PRIVMSG);
        this.channel = channel;
        this.messageBuilder = messageBuilder;
    }

    @Override
    public String payload(Instant dispatchInstant) {
        final String message = messageBuilder.apply(dispatchInstant);
        PrivMsgValidator.validate(message);
        return "PRIVMSG #"+channel.getName()+" :"+message;
    }
}
