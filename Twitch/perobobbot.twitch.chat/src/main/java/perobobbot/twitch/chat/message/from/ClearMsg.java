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
public class ClearMsg extends KnownMessageFromTwitch implements ChannelSpecific {


    @NonNull
    private final String login;

    @NonNull
    private final String clearedMessage;

    @NonNull
    private final String targetMsgId;

    @NonNull
    private final TwitchChannel channel;

    @Builder
    public ClearMsg(@NonNull IRCParsing ircParsing, @NonNull String login, @NonNull String clearedMessage, @NonNull String targetMsgId, @NonNull TwitchChannel channel) {
        super(ircParsing);
        this.login = login;
        this.clearedMessage = clearedMessage;
        this.targetMsgId = targetMsgId;
        this.channel = channel;
    }

    @Override
    public @NonNull IRCCommand getCommand() {
        return IRCCommand.CLEARMSG;
    }

    @Override
    public <T> T accept(@NonNull MessageFromTwitchVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public static @NonNull ClearMsg build(@NonNull AnswerBuilderHelper helper) {
        return ClearMsg.builder()
                       .ircParsing(helper.getIrcParsing())
                       .channel(helper.channelFromParameterAt(0))
                       .clearedMessage(helper.lastParameter())
                       .login(helper.tagValue(TagKey.LOGIN))
                       .targetMsgId(helper.tagValue(TagKey.TARGET_MSG_ID))
                       .build();
    }

}
