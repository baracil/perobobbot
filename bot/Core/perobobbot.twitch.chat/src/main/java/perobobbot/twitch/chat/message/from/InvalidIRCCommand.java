package perobobbot.twitch.chat.message.from;

import fpc.tools.irc.IRCParsing;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import perobobbot.twitch.chat.message.IRCCommand;

@ToString
public class InvalidIRCCommand extends KnownMessageFromTwitch {

    @Getter
    private final String user;

    @Getter
    private final String requestedCommand;

    @Builder
    public InvalidIRCCommand(IRCParsing ircParsing, String user, String requestedCommand) {
        super(ircParsing);
        this.user = user;
        this.requestedCommand = requestedCommand;
    }

    @Override
    public IRCCommand getCommand() {
        return IRCCommand.ERR_UNKNOWNCOMMAND;
    }

    @Override
    public <T> T accept(MessageFromTwitchVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public static InvalidIRCCommand build(AnswerBuilderHelper helper) {
        return InvalidIRCCommand.builder()
                .ircParsing(helper.getIrcParsing())
                .user(helper.parameterAt(0))
                .requestedCommand(helper.parameterAt(1))
                .build();
    }

}
