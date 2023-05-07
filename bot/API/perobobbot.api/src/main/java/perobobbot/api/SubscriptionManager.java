package perobobbot.api;

import perobobbot.api.data.Platform;

public interface SubscriptionManager {

    String SYSTEM_SUBSCRIPTION_TOPIC = "perobobbot:system/subscription";
    //TODO find a better topic name
    String SUBSCRIPTION_EVENT_TOPIC = "perobobbot:system/eventsub";

    String getCallbackUrl(Platform platform);

    void requestSynchronization(Platform platform);

    void requestSynchronizationForAllPlatforms();
}
