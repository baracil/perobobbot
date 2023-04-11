package perobobbot.twitch.chat.message.to;

import lombok.NonNull;
import perobobbot.twitch.chat.message.Capability;
import perobobbot.twitch.chat.message.IRCCommand;
import perobobbot.twitch.chat.message.from.CapAck;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Bastien Aracil
 **/
public final class Cap extends SimpleRequestToTwitch<CapAck> {

    @NonNull
    private final Set<Capability> capabilities;

    public Cap(@NonNull Capability... capabilities) {
        this(Set.of(capabilities));
    }

    public Cap(@NonNull Set<Capability> capabilities) {
        super(IRCCommand.CAP, CapAck.class);
        this.capabilities = capabilities;
    }

    @Override
    public @NonNull String payload(@NonNull Instant dispatchInstant) {
        return "CAP REQ :" + capabilities.stream()
                                         .map(Capability::getIrcValue)
                                         .collect(Collectors.joining(" "));
    }

    @Override
    protected @NonNull Optional<CapAck> doIsMyAnswer(@NonNull CapAck twitchAnswer, @NonNull String nick) {
        return Optional.of(twitchAnswer);
    }
}
