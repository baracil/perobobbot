package perobobbot.twitch.chat.message.from;

import fpc.tools.irc.IRCParsing;
import lombok.Builder;
import lombok.Getter;
import perobobbot.twitch.chat.ChannelSpecific;
import perobobbot.twitch.chat.TwitchChannel;
import perobobbot.twitch.chat.TwitchConstants;
import perobobbot.twitch.chat.message.IRCCommand;
import perobobbot.twitch.chat.message.TagKey;

import java.util.stream.Stream;

/**
 * @author Bastien Aracil
 **/
@Getter
public class Notice extends KnownMessageFromTwitch implements ChannelSpecific {

    private final TwitchChannel channel;

    private final NoticeId msgId;

    private final String message;

    @Builder
    public Notice(IRCParsing ircParsing, TwitchChannel channel, NoticeId msgId, String message) {
        super(ircParsing);
        this.channel = channel;
        this.msgId = msgId;
        this.message = message;
    }

    @Override
    public IRCCommand getCommand() {
        return IRCCommand.NOTICE;
    }

    @Override
    public <T> T accept(MessageFromTwitchVisitor<T> visitor) {
        return visitor.visit(this);
    }


    public static Notice build(AnswerBuilderHelper helper) {
        final var lastParameter = helper.lastParameter();
        return Stream.of(TwitchConstants.CHAT_LOGIN_AUTHENTICATION_FAILED, TwitchConstants.CHAT_LOGIN_UNSUCCESSFUL)
                     .filter(lastParameter::equals)
                     .findAny()
                     .map(m -> createAuthenticationFailureNotice(helper, m))
                     .orElseGet(() -> createNoticeWithMsgId(helper)
                     );
    }

    private static Notice createAuthenticationFailureNotice(AnswerBuilderHelper helper, String failureMessage) {
        return new Notice(
                helper.getIrcParsing(),
                TwitchChannel.create(""),
                NoticeId.AUTHENTICATION_FAILED,
                failureMessage);
    }

    private static Notice createNoticeWithMsgId(AnswerBuilderHelper helper) {
        return Notice.builder()
                     .ircParsing(helper.getIrcParsing())
                     .channel(helper.channelFromParameterAt(0))
                     .message(helper.lastParameter())
                     .msgId(helper.tagValue(TagKey.MSG_ID, NoticeId::getNoticeId))
                     .build();
    }
}
