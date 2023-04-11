package perobobbot.api;

import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;
import lombok.Value;
import perobobbot.api.data.Platform;

import java.util.Map;

@Value
@Serdeable
public class SubscriptionView {
    long id;
    @NonNull Platform platform;
    @NonNull String subscriptionType;
    @NonNull Map<String,String> conditions;
    /**
     * Id of the subscription on the platform
     */
    @NonNull String subscriptionId;

    boolean enabled;
    /**
     * The url used for this subscription
     */
    @NonNull String callbackUrl;

    public boolean hasPlatformId() {
        return !subscriptionId.isEmpty();
    }

}
