package perobobbot.twitch.chat.message.from;

import fpc.tools.irc.IRCParsing;
import lombok.Builder;
import lombok.Getter;
import perobobbot.twitch.chat.ChannelSpecific;
import perobobbot.twitch.chat.TwitchChannel;
import perobobbot.twitch.chat.message.IRCCommand;

/**
 * @author Bastien Aracil
 **/
@Getter
public class Part extends KnownMessageFromTwitch implements ChannelSpecific {

    private final String user;

    private final TwitchChannel channel;

    @Builder
    public Part(IRCParsing ircParsing, String user, TwitchChannel channel) {
        super(ircParsing);
        this.user = user;
        this.channel = channel;
    }

    @Override
    public IRCCommand getCommand() {
        return IRCCommand.PART;
    }

    @Override
    public <T> T accept(MessageFromTwitchVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public static Part build(AnswerBuilderHelper helper) {
        return Part.builder()
                   .ircParsing(helper.getIrcParsing())
                   .channel(helper.channelFromParameterAt(0))
                   .user(helper.userFromPrefix())
                   .build();
    }
}
