package perobobbot.twitch.chat.message.from;

import fpc.tools.irc.IRCParsing;
import lombok.Getter;
import lombok.ToString;
import perobobbot.twitch.chat.message.IRCCommand;

/**
 * @author Bastien Aracil
 **/
@Getter
@ToString
public class GenericKnownMessageFromTwitch extends KnownMessageFromTwitch {

    private final IRCCommand command;

    public GenericKnownMessageFromTwitch(IRCParsing ircParsing, IRCCommand command) {
        super(ircParsing);
        this.command = command;
    }

    @Override
    public <T> T accept(MessageFromTwitchVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
