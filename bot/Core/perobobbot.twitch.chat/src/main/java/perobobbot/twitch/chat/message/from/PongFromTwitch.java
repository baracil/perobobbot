package perobobbot.twitch.chat.message.from;

import fpc.tools.irc.IRCParsing;
import lombok.NonNull;
import perobobbot.twitch.chat.message.IRCCommand;

/**
 * @author Bastien Aracil
 **/
public class PongFromTwitch extends KnownMessageFromTwitch {

    public PongFromTwitch(@NonNull IRCParsing ircParsing) {
        super(ircParsing);
    }

    @Override
    public @NonNull IRCCommand getCommand() {
        return IRCCommand.PONG;
    }

    @Override
    public <T> T accept(@NonNull MessageFromTwitchVisitor<T> visitor) {
        return visitor.visit(this);
    }


    public static @NonNull MessageFromTwitch build(@NonNull AnswerBuilderHelper helper) {
        return new PongFromTwitch(helper.getIrcParsing());
    }
}
