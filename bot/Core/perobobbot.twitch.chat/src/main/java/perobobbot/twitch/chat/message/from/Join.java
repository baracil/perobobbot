package perobobbot.twitch.chat.message.from;

import fpc.tools.irc.IRCParsing;
import lombok.Builder;
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
public class Join extends KnownMessageFromTwitch implements ChannelSpecific {


    private final String user;

    private final TwitchChannel channel;

    @Builder
    public Join(IRCParsing ircParsing, String user, TwitchChannel channel) {
        super(ircParsing);
        this.user = user;
        this.channel = channel;
    }

    @Override
    public IRCCommand getCommand() {
        return IRCCommand.JOIN;
    }

    @Override
    public <T> T accept(MessageFromTwitchVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public static Join build(AnswerBuilderHelper helper) {
        return Join.builder()
                   .ircParsing(helper.getIrcParsing())
                   .user(helper.userFromPrefix())
                   .channel(helper.channelFromParameterAt(0))
                   .build();
    }
}
