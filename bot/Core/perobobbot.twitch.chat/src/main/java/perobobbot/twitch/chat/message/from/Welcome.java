package perobobbot.twitch.chat.message.from;

import fpc.tools.irc.IRCParsing;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import perobobbot.twitch.chat.message.IRCCommand;

/**
 * @author Bastien Aracil
 **/
@ToString
public class Welcome extends KnownMessageFromTwitch {

    @Getter
    private final String userName;

    @Builder
    public Welcome(IRCParsing ircParsing, String userName) {
        super(ircParsing);
        this.userName = userName;
    }

    @Override
    public IRCCommand getCommand() {
        return IRCCommand.RPL_WELCOME;
    }

    @Override
    public <T> T accept(MessageFromTwitchVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public static Welcome build(AnswerBuilderHelper helper) {
        return Welcome.builder()
                .ircParsing(helper.getIrcParsing())
                .userName(helper.parameterAt(0))
                .build();
    }
}
