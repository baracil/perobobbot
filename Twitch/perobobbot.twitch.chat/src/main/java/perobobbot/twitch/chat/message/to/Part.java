package perobobbot.twitch.chat.message.to;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.twitch.chat.TwitchChannel;
import perobobbot.twitch.chat.message.IRCCommand;

import java.time.Instant;
import java.util.Optional;

/**
 * @author Bastien Aracil
 **/
@Getter
public class Part extends SimpleRequestToTwitch<perobobbot.twitch.chat.message.from.Part> {

    @NonNull
    @Getter
    private final TwitchChannel channel;

    public Part(@NonNull TwitchChannel channel) {
        super(IRCCommand.PART, perobobbot.twitch.chat.message.from.Part.class);
        this.channel = channel;
    }

    @Override
    public @NonNull String payload(@NonNull Instant dispatchInstant) {
        return "PART #"+channel.getName();
    }

    @Override
    @NonNull
    protected Optional<perobobbot.twitch.chat.message.from.Part> doIsMyAnswer(@NonNull perobobbot.twitch.chat.message.from.Part twitchAnswer,
                                                                              @NonNull String nick) {
        if (twitchAnswer.getChannel().equals(channel) && twitchAnswer.getUser().equals(nick)) {
            return Optional.of(twitchAnswer);
        }
        return Optional.empty();
    }
}
