package perobobbot.command.api;

import fpc.tools.lang.Subscription;
import lombok.NonNull;
import perobobbot.api.data.UserIdentity;

public interface CommandManager {

    int VERSION = 1;


    @NonNull Subscription addCommand(@NonNull String command,
                                     @NonNull CommandAction action);

    void handleMessage(@NonNull UserIdentity sender, @NonNull String channelName, @NonNull String message);
}
