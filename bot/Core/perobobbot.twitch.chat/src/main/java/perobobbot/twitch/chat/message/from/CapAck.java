package perobobbot.twitch.chat.message.from;

import fpc.tools.irc.IRCParsing;
import lombok.Getter;
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


    private final Set<Capability> capabilities;

    public CapAck(IRCParsing ircParsing, Set<Capability> capabilities) {
        super(ircParsing);
        this.capabilities = capabilities;
    }

    @Override
    public IRCCommand getCommand() {
        return IRCCommand.CAP;
    }

    @Override
    public <T> T accept(MessageFromTwitchVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public static CapAck build(AnswerBuilderHelper helper) {
        return new CapAck(helper.getIrcParsing(), helper.capabilities());
    }


}
