package perobobbot.api;

import perobobbot.api.data.Platform;

public interface PlatformSubscriptionManager {

    Platform getPlatform();

    String getCallbackUrl();
}
