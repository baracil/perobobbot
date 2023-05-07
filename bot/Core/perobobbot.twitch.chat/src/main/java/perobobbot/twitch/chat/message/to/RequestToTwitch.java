package perobobbot.twitch.chat.message.to;

import fpc.tools.advanced.chat.Request;
import fpc.tools.fp.TryResult;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import perobobbot.twitch.chat.message.IRCCommand;
import perobobbot.twitch.chat.message.from.MessageFromTwitch;

import java.util.Optional;

/**
 * @author Bastien Aracil
 **/
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
public abstract class RequestToTwitch<A> extends MessageToTwitch implements Request<A> {

    private final IRCCommand command;

    private final Class<A> answerType;

    public abstract Optional<TryResult<Throwable,A>> isMyAnswer(MessageFromTwitch messageFromTwitch,
                                                                String nick);

    @Override
    public String commandInPayload() {
        return command.name();
    }
}
