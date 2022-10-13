package perobobbot.callback.api;

import fpc.tools.lang.Subscription;
import lombok.NonNull;

import java.util.Optional;

public interface CallbackManager {

    @NonNull Optional<Callback> findCallback(@NonNull String id);

    @NonNull Subscription addCallback(@NonNull String id, @NonNull Callback callback);

}
