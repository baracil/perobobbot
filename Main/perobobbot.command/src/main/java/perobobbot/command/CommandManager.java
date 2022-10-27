package perobobbot.command;

import fpc.tools.fp.Consumer1;
import fpc.tools.lang.Subscription;
import lombok.NonNull;
import perobobbot.api.data.view.UserIdentity;

public interface CommandManager {

    @NonNull Subscription addCommand(@NonNull String command,
                                     @NonNull Consumer1<? super CommandBinding> execution);

    void handleMessage(@NonNull UserIdentity sender, @NonNull String message);
}
