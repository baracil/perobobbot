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
public class ClearMsg extends KnownMessageFromTwitch implements ChannelSpecific {


    private final String login;

    private final String clearedMessage;

    private final String targetMsgId;

    private final TwitchChannel channel;

    @Builder
    public ClearMsg(IRCParsing ircParsing, String login, String clearedMessage, String targetMsgId, TwitchChannel channel) {
        super(ircParsing);
        this.login = login;
        this.clearedMessage = clearedMessage;
        this.targetMsgId = targetMsgId;
        this.channel = channel;
    }

    @Override
    public IRCCommand getCommand() {
        return IRCCommand.CLEARMSG;
    }

    @Override
    public <T> T accept(MessageFromTwitchVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public static ClearMsg build(AnswerBuilderHelper helper) {
        return ClearMsg.builder()
                       .ircParsing(helper.getIrcParsing())
                       .channel(helper.channelFromParameterAt(0))
                       .clearedMessage(helper.lastParameter())
                       .login(helper.tagValue(TagKey.LOGIN))
                       .targetMsgId(helper.tagValue(TagKey.TARGET_MSG_ID))
                       .build();
    }

}
