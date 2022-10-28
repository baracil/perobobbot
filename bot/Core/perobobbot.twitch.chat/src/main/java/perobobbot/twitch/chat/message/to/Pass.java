package perobobbot.twitch.chat.message.to;

import fpc.tools.lang.Secret;
import lombok.NonNull;
import perobobbot.twitch.chat.message.IRCCommand;

import java.time.Instant;

/**
 * @author Bastien Aracil
 **/
public class Pass extends CommandToTwitch {

    @NonNull
    private final Secret oauthValue;

    public Pass(@NonNull Secret oauthValue) {
        super(IRCCommand.PASS);
        this.oauthValue = oauthValue;
    }

    @Override
    public @NonNull String payload(@NonNull Instant dispatchInstant) {
        return "PASS oauth:" + oauthValue.value();
    }

}
