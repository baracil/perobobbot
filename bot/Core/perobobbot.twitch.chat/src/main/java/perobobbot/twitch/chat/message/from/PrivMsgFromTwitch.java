package perobobbot.twitch.chat.message.from;

import fpc.tools.irc.IRCParsing;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import perobobbot.twitch.chat.ChannelSpecific;
import perobobbot.twitch.chat.TwitchChannel;
import perobobbot.twitch.chat.message.IRCCommand;
import perobobbot.twitch.chat.message.TagKey;

/**
 * @author Bastien Aracil
 **/
@Getter
@ToString
public class PrivMsgFromTwitch extends KnownMessageFromTwitch implements ChannelSpecific {

    //TODO use user-id in message tag instead of user in message prefix to identify permanently the user on Twitch as
    //the user can change its pseudo

    private final String user;

    private final TwitchChannel channel;

    private final String payload;

    @Builder
    public PrivMsgFromTwitch(IRCParsing ircParsing, String user, TwitchChannel channel, String payload) {
        super(ircParsing);
        this.user = user;
        this.channel = channel;
        this.payload = payload;
    }

    @Override
    public IRCCommand getCommand() {
        return IRCCommand.PRIVMSG;
    }

    public String getChannelName() {
        return channel.getName();
    }

    @Override
    public <T> T accept(MessageFromTwitchVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public static PrivMsgFromTwitch build(AnswerBuilderHelper helper) {
        return PrivMsgFromTwitch.builder()
                                .ircParsing(helper.getIrcParsing())
                                .user(helper.tagValue(TagKey.USER_ID))
                                .channel(helper.channelFromParameterAt(0))
                                .payload(helper.lastParameter())
                                .build();
    }

}
