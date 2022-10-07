package perobobbot.twitch.chat.message.from;

import fpc.tools.irc.IRCParsing;
import lombok.NonNull;
import perobobbot.twitch.chat.message.IRCCommand;

/**
 * @author Bastien Aracil
 **/
public abstract class KnownMessageFromTwitch extends MessageFromTwitchBase implements MessageFromTwitch {

    public KnownMessageFromTwitch(@NonNull IRCParsing ircParsing) {
        super(ircParsing);
    }

    @NonNull
    public abstract IRCCommand getCommand();

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + getPayload() + "}";
    }
}
