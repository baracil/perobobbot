package perobobbot.api;

import lombok.NonNull;
import perobobbot.api.data.Platform;

public interface SubscriptionManager {

    @NonNull String getCallbackUrl(@NonNull Platform platform);

    void requestSynchronization(@NonNull Platform platform);

}
