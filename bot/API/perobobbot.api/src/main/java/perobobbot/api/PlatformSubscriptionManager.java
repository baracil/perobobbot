package perobobbot.api;

import lombok.NonNull;
import perobobbot.api.data.Platform;

public interface PlatformSubscriptionManager {

    @NonNull Platform getPlatform();

    @NonNull String getCallbackUrl();
}
