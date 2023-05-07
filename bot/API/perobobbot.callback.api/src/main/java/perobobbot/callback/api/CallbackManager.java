package perobobbot.callback.api;

import fpc.tools.lang.Subscription;

import java.util.Optional;

public interface CallbackManager {

    Optional<Callback> findCallback(String id);

    Subscription addCallback(String id, Callback callback);

}
