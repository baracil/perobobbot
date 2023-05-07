package perobobbot.twitch.chat.message.to;

import fpc.tools.fp.TryResult;
import perobobbot.twitch.chat.TwitchChatAuthenticationFailure;
import perobobbot.twitch.chat.TwitchConstants;
import perobobbot.twitch.chat.message.IRCCommand;
import perobobbot.twitch.chat.message.from.GlobalUserState;
import perobobbot.twitch.chat.message.from.MessageFromTwitch;
import perobobbot.twitch.chat.message.from.Notice;

import java.time.Instant;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Bastien Aracil
 **/
public class Nick extends RequestToTwitch<GlobalUserState> {

    private final String nickname;

    public Nick(String nickname) {
        super(IRCCommand.NICK, GlobalUserState.class);
        this.nickname = nickname;
    }

    @Override
    public String payload(Instant dispatchInstant) {
        return "NICK " + nickname;
    }

    @Override
    public Optional<TryResult<Throwable,GlobalUserState>> isMyAnswer(
            MessageFromTwitch messageFromTwitch,
            String nick) {
        if (messageFromTwitch instanceof GlobalUserState globalUserState) {
            return checkGlobalUserState(globalUserState);
        } else if (messageFromTwitch instanceof Notice notice) {
            return checkNotice(notice);
        }
        return Optional.empty();
    }

    private Optional<TryResult<Throwable,GlobalUserState>> checkGlobalUserState(GlobalUserState globalUserState) {
        return Optional.of(TryResult.success(globalUserState));
    }

    private Optional<TryResult<Throwable,GlobalUserState>> checkNotice(Notice notice) {
        if (Stream.of(TwitchConstants.CHAT_LOGIN_AUTHENTICATION_FAILED,
                      TwitchConstants.CHAT_IMPROPERLY_FORMATTED_AUTH)
                  .anyMatch(m -> notice.getMessage().equals(m))) {
            return Optional.of(TryResult.failure(new TwitchChatAuthenticationFailure(notice.getMessage())));
        }
        return Optional.empty();
    }
}
