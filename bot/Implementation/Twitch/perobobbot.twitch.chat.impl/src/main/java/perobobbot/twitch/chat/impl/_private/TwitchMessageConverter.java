package perobobbot.twitch.chat.impl._private;

import fpc.tools.advanced.chat.MessageConverter;
import fpc.tools.irc.IRCParser;
import fpc.tools.irc.IRCParsing;
import fpc.tools.lang.ThrowableTool;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import perobobbot.twitch.api.TwitchMarkers;
import perobobbot.twitch.chat.message.IRCCommand;
import perobobbot.twitch.chat.message.from.AnswerBuilderHelper;
import perobobbot.twitch.chat.message.from.MessageFromTwitch;
import perobobbot.twitch.chat.message.from.UnknownMessageFromTwitch;

import java.util.Optional;

/**
 * @author Bastien Aracil
 **/
@Slf4j
public class TwitchMessageConverter implements MessageConverter<MessageFromTwitch> {

    @NonNull
    private final IRCParser ircParser;

    public TwitchMessageConverter() {
        this.ircParser = IRCParser.createForPlugin();
    }

    /**
     * Convert a message from the twitch chat to a {@link MessageFromTwitch}
     * @param messageAsString the text message
     * @return the message from Twitch
     */
    @Override
    public @NonNull Optional<MessageFromTwitch> convert(@NonNull String messageAsString) {
        try {
            final IRCParsing ircParsing = ircParser.parse(messageAsString);
            final Optional<IRCCommand> command = IRCCommand.findFromString(ircParsing.getCommand());

            final MessageFromTwitch messageFromTwitch = command.map(cmd -> buildKnownAnswer(cmd, ircParsing))
                                                               .orElseGet(() -> buildUnknownAnswer(ircParsing));

            return Optional.of(messageFromTwitch);
        } catch (Exception e) {
            e.printStackTrace();
            ThrowableTool.interruptIfCausedByInterruption(e);
            LOG.warn(TwitchMarkers.TWITCH_CHAT, "Fail to convert message '" + messageAsString + "'", e);
            return Optional.empty();
        }
    }

    @NonNull
    private MessageFromTwitch buildKnownAnswer(@NonNull IRCCommand command, @NonNull IRCParsing ircParsing) {
        final AnswerBuilderHelper helper = new AnswerBuilderHelper(ircParsing);
        return command.buildTwitchAnswer(helper);
    }

    @NonNull
    private MessageFromTwitch buildUnknownAnswer(@NonNull IRCParsing ircParsing) {
        LOG.warn(TwitchMarkers.TWITCH_CHAT, "Unknown command : '{}' for message '{}'", ircParsing.getCommand(), ircParsing.getRawMessage());
        return new UnknownMessageFromTwitch(ircParsing);
    }

}
