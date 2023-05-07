package perobobbot.command.api;

import fpc.tools.lang.Subscription;

public interface CommandRegistry {

    Subscription addCommand(String command);


}
