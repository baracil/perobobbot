package perobobbot.twitch.chat.message.from;

import fpc.tools.irc.IRCParsing;
import perobobbot.twitch.chat.message.IRCCommand;

/**
 * @author Bastien Aracil
 **/
public class PongFromTwitch extends KnownMessageFromTwitch {

    public PongFromTwitch(IRCParsing ircParsing) {
        super(ircParsing);
    }

    @Override
    public IRCCommand getCommand() {
        return IRCCommand.PONG;
    }

    @Override
    public <T> T accept(MessageFromTwitchVisitor<T> visitor) {
        return visitor.visit(this);
    }


    public static MessageFromTwitch build(AnswerBuilderHelper helper) {
        return new PongFromTwitch(helper.getIrcParsing());
    }
}
