package perobobbot.twitch.chat.message.to;

import fpc.tools.lang.Secret;
import perobobbot.twitch.chat.message.IRCCommand;

import java.time.Instant;

/**
 * @author Bastien Aracil
 **/
public class Pass extends CommandToTwitch {

    private final Secret oauthValue;

    public Pass(Secret oauthValue) {
        super(IRCCommand.PASS);
        this.oauthValue = oauthValue;
    }

    @Override
    public String payload(Instant dispatchInstant) {
        return "PASS oauth:" + oauthValue.value();
    }

}
