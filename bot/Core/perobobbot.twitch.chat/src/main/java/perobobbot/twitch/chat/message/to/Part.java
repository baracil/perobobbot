package perobobbot.twitch.chat.message.to;

import lombok.Getter;
import perobobbot.twitch.chat.TwitchChannel;
import perobobbot.twitch.chat.message.IRCCommand;

import java.time.Instant;
import java.util.Optional;

/**
 * @author Bastien Aracil
 **/
@Getter
public class Part extends SimpleRequestToTwitch<perobobbot.twitch.chat.message.from.Part> {

    @Getter
    private final TwitchChannel channel;

    public Part(TwitchChannel channel) {
        super(IRCCommand.PART, perobobbot.twitch.chat.message.from.Part.class);
        this.channel = channel;
    }

    @Override
    public String payload(Instant dispatchInstant) {
        return "PART #"+channel.getName();
    }

    @Override
    protected Optional<perobobbot.twitch.chat.message.from.Part> doIsMyAnswer(perobobbot.twitch.chat.message.from.Part twitchAnswer,
                                                                              String nick) {
        if (twitchAnswer.getChannel().equals(channel) && twitchAnswer.getUser().equals(nick)) {
            return Optional.of(twitchAnswer);
        }
        return Optional.empty();
    }
}
