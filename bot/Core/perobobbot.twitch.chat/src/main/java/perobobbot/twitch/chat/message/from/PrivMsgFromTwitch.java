package perobobbot.twitch.chat.message.from;

import fpc.tools.irc.IRCParsing;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
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

    @NonNull
    private final String user;

    @NonNull
    private final TwitchChannel channel;

    @NonNull
    private final String payload;

    @Builder
    public PrivMsgFromTwitch(@NonNull IRCParsing ircParsing, @NonNull String user, @NonNull TwitchChannel channel, @NonNull String payload) {
        super(ircParsing);
        this.user = user;
        this.channel = channel;
        this.payload = payload;
    }

    @Override
    public @NonNull IRCCommand getCommand() {
        return IRCCommand.PRIVMSG;
    }

    public @NonNull String getChannelName() {
        return channel.getName();
    }

    @Override
    public <T> T accept(@NonNull MessageFromTwitchVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @NonNull
    public static PrivMsgFromTwitch build(@NonNull AnswerBuilderHelper helper) {
        return PrivMsgFromTwitch.builder()
                                .ircParsing(helper.getIrcParsing())
                                .user(helper.tagValue(TagKey.USER_ID))
                                .channel(helper.channelFromParameterAt(0))
                                .payload(helper.lastParameter())
                                .build();
    }

}
