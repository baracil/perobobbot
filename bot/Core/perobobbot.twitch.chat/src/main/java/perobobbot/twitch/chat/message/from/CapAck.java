package perobobbot.twitch.chat.message.from;

import fpc.tools.irc.IRCParsing;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import perobobbot.twitch.chat.message.Capability;
import perobobbot.twitch.chat.message.IRCCommand;

import java.util.Set;

/**
 * @author Bastien Aracil
 **/
@Getter
@ToString
public class CapAck extends KnownMessageFromTwitch {


    @NonNull
    private final Set<Capability> capabilities;

    public CapAck(@NonNull IRCParsing ircParsing, @NonNull Set<Capability> capabilities) {
        super(ircParsing);
        this.capabilities = capabilities;
    }

    @Override
    public @NonNull IRCCommand getCommand() {
        return IRCCommand.CAP;
    }

    @Override
    public <T> T accept(@NonNull MessageFromTwitchVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @NonNull
    public static CapAck build(@NonNull AnswerBuilderHelper helper) {
        return new CapAck(helper.getIrcParsing(), helper.capabilities());
    }


}
