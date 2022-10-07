package perobobbot.twitch.chat.message.to;

import lombok.NonNull;
import perobobbot.twitch.chat.message.IRCCommand;

import java.time.Instant;

public class Pong extends CommandToTwitch {

    public static Pong create() {
        return new Pong();
    }

    private Pong() {
        super(IRCCommand.PONG);
    }

    @Override
    public String commandInPayload() {
        return "PONG";
    }

    @Override
    public @NonNull String payload(@NonNull Instant dispatchInstant) {
        return "PONG :tmi.twitch.tv";
    }

}
