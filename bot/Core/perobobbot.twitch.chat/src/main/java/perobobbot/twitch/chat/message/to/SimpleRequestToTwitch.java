package perobobbot.twitch.chat.message.to;

import fpc.tools.fp.TryResult;
import fpc.tools.lang.CastTool;
import lombok.NonNull;
import perobobbot.twitch.chat.message.IRCCommand;
import perobobbot.twitch.chat.message.from.MessageFromTwitch;

import java.util.Optional;

/**
 * @author Bastien Aracil
 **/
public abstract class SimpleRequestToTwitch<A> extends RequestToTwitch<A> {

    private final Class<A> expectedAnswerType;

    SimpleRequestToTwitch(@NonNull IRCCommand command, @NonNull Class<A> expectedAnswerType) {
        super(command, expectedAnswerType);
        this.expectedAnswerType = expectedAnswerType;
    }


    @Override
    public final @NonNull Optional<TryResult<Throwable,A>> isMyAnswer(@NonNull MessageFromTwitch messageFromTwitch, @NonNull String nick) {
        return CastTool.as(expectedAnswerType, messageFromTwitch)
                       .flatMap(a -> doIsMyAnswer(a, nick))
                       .map(TryResult::success);
    }

    @NonNull
    protected abstract Optional<A> doIsMyAnswer(@NonNull A twitchAnswer, @NonNull String nick);
}
