package perobobbot.twitch.chat.impl._private;

import fpc.tools.advanced.chat.Request;
import fpc.tools.advanced.chat.RequestAnswerMatcher;
import fpc.tools.fp.TryResult;
import fpc.tools.lang.ThrowableTool;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import perobobbot.twitch.api.TwitchMarkers;
import perobobbot.twitch.chat.UnknownIRCCommand;
import perobobbot.twitch.chat.message.IRCCommand;
import perobobbot.twitch.chat.message.from.InvalidIRCCommand;
import perobobbot.twitch.chat.message.from.KnownMessageFromTwitch;
import perobobbot.twitch.chat.message.from.MessageFromTwitch;
import perobobbot.twitch.chat.message.to.RequestToTwitch;

import java.util.Optional;

/**
 * @author Bastien Aracil
 **/
@Slf4j
@RequiredArgsConstructor
public class TwitchMatcher implements RequestAnswerMatcher<MessageFromTwitch> {

    @NonNull
    private final String nick;

    @Override
    public @NonNull <A> Optional<TryResult<Throwable,A>> performMatch(@NonNull Request<A> request, @NonNull MessageFromTwitch answer) {
        if (request instanceof RequestToTwitch<A> requestToTwitch) {
            try {
                if (answer instanceof InvalidIRCCommand) {
                    return handleInvalidAnswerType(requestToTwitch, (InvalidIRCCommand) answer);
                }
                return performMatch(requestToTwitch, answer);
            } catch (Exception e) {
                ThrowableTool.interruptIfCausedByInterruption(e);
                LOG.warn(TwitchMarkers.TWITCH_CHAT, "Error while performing message matching",e);
            }
        }
        return Optional.empty();
    }

    @NonNull
    private <A> Optional<TryResult<Throwable,A>> handleInvalidAnswerType(RequestToTwitch<A> request, InvalidIRCCommand answer) {
        final String unknownCommand = answer.getRequestedCommand();
        if (unknownCommand.equalsIgnoreCase(request.commandInPayload())) {
            return Optional.of(TryResult.failure(new UnknownIRCCommand(request.commandInPayload())));
        }
        return Optional.empty();
    }

    @Override
    public boolean shouldPerformMatching(@NonNull MessageFromTwitch message) {
        if (message instanceof final KnownMessageFromTwitch messageFromTwitch) {
            return messageFromTwitch.getCommand() != IRCCommand.PRIVMSG;
        }
        return false;
    }

    @NonNull
    private <A> Optional<TryResult<Throwable,A>> performMatch(@NonNull RequestToTwitch<A> request, @NonNull MessageFromTwitch answer) {
        return request.isMyAnswer(answer, nick);
    }


}
