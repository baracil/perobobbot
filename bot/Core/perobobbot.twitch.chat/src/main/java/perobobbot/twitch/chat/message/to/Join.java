package perobobbot.twitch.chat.message.to;

import fpc.tools.fp.TryResult;
import perobobbot.twitch.chat.ChannelSpecific;
import perobobbot.twitch.chat.JoinFailure;
import perobobbot.twitch.chat.TwitchChannel;
import perobobbot.twitch.chat.message.IRCCommand;
import perobobbot.twitch.chat.message.from.MessageFromTwitch;
import perobobbot.twitch.chat.message.from.Notice;
import perobobbot.twitch.chat.message.from.NoticeId;
import perobobbot.twitch.chat.message.from.UserState;

import java.time.Instant;
import java.util.Optional;

/**
 * @author Bastien Aracil
 **/
public class Join extends RequestToTwitch<UserState> {

    private final TwitchChannel channel;

    public Join(TwitchChannel channel) {
        super(IRCCommand.JOIN, UserState.class);
        this.channel = channel;
    }

    @Override
    public String payload(Instant dispatchInstant) {
        return "JOIN #"+channel.getName();
    }

    @Override
    public Optional<TryResult<Throwable,UserState>> isMyAnswer(MessageFromTwitch messageFromTwitch,
                                                                        String nick) {
        if (appliesToMyChannel(messageFromTwitch)) {

            if (messageFromTwitch instanceof Notice notice) {
                return checkNotice(notice);
            }

            if (messageFromTwitch instanceof UserState) {
                return checkUserState((UserState) messageFromTwitch);
            }
        }
        return Optional.empty();
    }

    private boolean appliesToMyChannel(MessageFromTwitch messageFromTwitch) {
        if (messageFromTwitch instanceof ChannelSpecific channelSpecific) {
            return channelSpecific.getChannel().equals(channel);
        }
        return false;
    }

    private Optional<TryResult<Throwable,UserState>> checkNotice(Notice notice) {
        if (notice.getMsgId() == NoticeId.MSG_CHANNEL_SUSPENDED) {
            return Optional.of(TryResult.failure(new JoinFailure(notice.getMsgId(), notice.getMessage())));
        }
        return Optional.empty();
    }

    private Optional<TryResult<Throwable,UserState>> checkUserState(UserState userState) {
        return Optional.of(TryResult.success(userState));
    }

}
