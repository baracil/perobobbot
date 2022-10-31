package perobobbot.command.api;

import fpc.tools.fp.Consumer1;
import fpc.tools.lang.Subscription;
import lombok.NonNull;
import perobobbot.api.data.UserIdentity;

public interface CommandManager {

    int VERSION = 1;


    @NonNull Subscription addCommand(@NonNull String command,
                                     @NonNull Consumer1<? super CommandBinding> execution);

    void handleMessage(@NonNull UserIdentity sender, @NonNull String channelName, @NonNull String message);
}
