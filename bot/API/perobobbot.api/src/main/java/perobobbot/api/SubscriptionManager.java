package perobobbot.api;

import lombok.NonNull;
import perobobbot.api.data.Platform;

public interface SubscriptionManager {

    String SYSTEM_SUBSCRIPTION_TOPIC = "perobobbot:system/subscription";
    //TODO find a better topic name
    String SUBSCRIPTION_EVENT_TOPIC = "perobobbot:system/eventsub";

    @NonNull String getCallbackUrl(@NonNull Platform platform);

    void requestSynchronization(@NonNull Platform platform);

    void requestSynchronizationForAllPlatforms();
}
