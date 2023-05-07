package perobobbot.twitch.chat.message.to;

import fpc.tools.fp.TryResult;
import fpc.tools.lang.CastTool;
import perobobbot.twitch.chat.message.IRCCommand;
import perobobbot.twitch.chat.message.from.MessageFromTwitch;

import java.util.Optional;

/**
 * @author Bastien Aracil
 **/
public abstract class SimpleRequestToTwitch<A> extends RequestToTwitch<A> {

    private final Class<A> expectedAnswerType;

    SimpleRequestToTwitch(IRCCommand command, Class<A> expectedAnswerType) {
        super(command, expectedAnswerType);
        this.expectedAnswerType = expectedAnswerType;
    }


    @Override
    public final Optional<TryResult<Throwable,A>> isMyAnswer(MessageFromTwitch messageFromTwitch, String nick) {
        return CastTool.as(expectedAnswerType, messageFromTwitch)
                       .flatMap(a -> doIsMyAnswer(a, nick))
                       .map(TryResult::success);
    }

    protected abstract Optional<A> doIsMyAnswer(A twitchAnswer, String nick);
}
