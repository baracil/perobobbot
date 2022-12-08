package perobobbot.command.api;

import fpc.tools.lang.Subscription;
import lombok.NonNull;

public interface CommandRegistry {

    @NonNull Subscription addCommand(@NonNull String command);


}
