package perobobbot.twitch.chat.message.to;

import lombok.NonNull;
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
    public @NonNull String payload(@NonNull Instant dispatchInstant) {
        return "PING :tmi.twitch.tv";
    }

    @Override
    protected @NonNull Optional<PongFromTwitch> doIsMyAnswer(@NonNull PongFromTwitch twitchAnswer,
                                                             @NonNull String nick) {
        return Optional.of(twitchAnswer);
    }
}
