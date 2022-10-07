package perobobbot.twitch.chat.message.from;

import fpc.tools.irc.IRCParsing;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
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

    @NonNull
    private final TwitchChannel channel;

    @NonNull
    private final NoticeId msgId;

    @NonNull
    private final String message;

    @Builder
    public Notice(@NonNull IRCParsing ircParsing, @NonNull TwitchChannel channel, @NonNull NoticeId msgId, @NonNull String message) {
        super(ircParsing);
        this.channel = channel;
        this.msgId = msgId;
        this.message = message;
    }

    @Override
    public @NonNull IRCCommand getCommand() {
        return IRCCommand.NOTICE;
    }

    @Override
    public <T> T accept(@NonNull MessageFromTwitchVisitor<T> visitor) {
        return visitor.visit(this);
    }


    public static @NonNull Notice build(@NonNull AnswerBuilderHelper helper) {
        final var lastParameter = helper.lastParameter();
        return Stream.of(TwitchConstants.CHAT_LOGIN_AUTHENTICATION_FAILED, TwitchConstants.CHAT_LOGIN_UNSUCCESSFUL)
                     .filter(lastParameter::equals)
                     .findAny()
                     .map(m -> createAuthenticationFailureNotice(helper, m))
                     .orElseGet(() -> createNoticeWithMsgId(helper)
                     );
    }

    private static @NonNull Notice createAuthenticationFailureNotice(@NonNull AnswerBuilderHelper helper, @NonNull String failureMessage) {
        return new Notice(
                helper.getIrcParsing(),
                TwitchChannel.create(""),
                NoticeId.AUTHENTICATION_FAILED,
                failureMessage);
    }

    private static @NonNull Notice createNoticeWithMsgId(@NonNull AnswerBuilderHelper helper) {
        return Notice.builder()
                     .ircParsing(helper.getIrcParsing())
                     .channel(helper.channelFromParameterAt(0))
                     .message(helper.lastParameter())
                     .msgId(helper.tagValue(TagKey.MSG_ID, NoticeId::getNoticeId))
                     .build();
    }
}
