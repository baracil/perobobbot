package perobobbot.twitch.chat.message.from;

import fpc.tools.irc.IRCParsing;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import perobobbot.twitch.chat.message.IRCCommand;

@Getter
@ToString
public class GlobalUserState extends KnownMessageFromTwitch {

    @Builder
    public GlobalUserState(IRCParsing ircParsing) {
        super(ircParsing);
    }

    public String getRawMessage() {
        return getIrcParsing().getRawMessage();
    }

    @Override
    public IRCCommand getCommand() {
        return IRCCommand.GLOBALUSERSTATE;
    }

    @Override
    public <T> T accept(MessageFromTwitchVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public static GlobalUserState build(AnswerBuilderHelper helper) {
        return GlobalUserState.builder()
                              .ircParsing(helper.getIrcParsing())
                              .build();
    }

}
