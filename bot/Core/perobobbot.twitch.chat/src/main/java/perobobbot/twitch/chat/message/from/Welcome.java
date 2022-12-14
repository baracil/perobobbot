package perobobbot.twitch.chat.message.from;

import fpc.tools.irc.IRCParsing;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import perobobbot.twitch.chat.message.IRCCommand;

/**
 * @author Bastien Aracil
 **/
@ToString
public class Welcome extends KnownMessageFromTwitch {

    @NonNull
    @Getter
    private final String userName;

    @Builder
    public Welcome(@NonNull IRCParsing ircParsing, @NonNull String userName) {
        super(ircParsing);
        this.userName = userName;
    }

    @Override
    public @NonNull IRCCommand getCommand() {
        return IRCCommand.RPL_WELCOME;
    }

    @Override
    public <T> T accept(@NonNull MessageFromTwitchVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @NonNull
    public static Welcome build(@NonNull AnswerBuilderHelper helper) {
        return Welcome.builder()
                .ircParsing(helper.getIrcParsing())
                .userName(helper.parameterAt(0))
                .build();
    }
}
