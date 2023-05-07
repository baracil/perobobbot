package perobobbot.twitch.chat.message.from;

import fpc.tools.irc.IRCParsing;
import perobobbot.twitch.chat.message.IRCCommand;

/**
 * @author Bastien Aracil
 **/
public class PingFromTwitch extends KnownMessageFromTwitch {


    public PingFromTwitch(IRCParsing ircParsing) {
        super(ircParsing);
    }

    @Override
    public IRCCommand getCommand() {
        return IRCCommand.PING;
    }

    @Override
    public <T> T accept(MessageFromTwitchVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public static  MessageFromTwitch build(AnswerBuilderHelper helper) {
        return new PingFromTwitch(helper.getIrcParsing());
    }
}
