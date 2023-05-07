package perobobbot.twitch.chat.message.from;

import fpc.tools.irc.IRCParsing;
import lombok.Getter;
import lombok.ToString;
import perobobbot.twitch.chat.ChannelSpecific;
import perobobbot.twitch.chat.TwitchChannel;
import perobobbot.twitch.chat.message.IRCCommand;

/**
 * @author Bastien Aracil
 **/
@Getter
@ToString
public class UserNotice extends KnownMessageFromTwitch implements ChannelSpecific {

    private final TwitchChannel channel;

    private final String message;

    public UserNotice(IRCParsing ircParsing, TwitchChannel channel, String message) {
        super(ircParsing);
        this.channel = channel;
        this.message = message;
    }

    @Override
    public IRCCommand getCommand() {
        return IRCCommand.USERNOTICE;
    }

    @Override
    public <T> T accept(MessageFromTwitchVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public static UserNotice build(AnswerBuilderHelper helper) {
        return new UserNotice(
                helper.getIrcParsing(),
                helper.channelFromParameterAt(0),
                helper.lastParameter()
        );
    }
}
