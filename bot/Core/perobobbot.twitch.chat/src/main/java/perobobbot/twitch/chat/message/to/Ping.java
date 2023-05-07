package perobobbot.twitch.chat.message.to;

import perobobbot.twitch.chat.message.IRCCommand;
import perobobbot.twitch.chat.message.from.PongFromTwitch;

import java.time.Instant;
import java.util.Optional;

public class Ping extends SimpleRequestToTwitch<PongFromTwitch> {

    public Ping() {
        super(IRCCommand.PING, PongFromTwitch.class);
    }

    @Override
    public String commandInPayload() {
        return "PING";
    }

    @Override
    public String payload(Instant dispatchInstant) {
        return "PING :tmi.twitch.tv";
    }

    @Override
    protected Optional<PongFromTwitch> doIsMyAnswer(PongFromTwitch twitchAnswer,
                                                             String nick) {
        return Optional.of(twitchAnswer);
    }
}
