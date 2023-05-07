package perobobbot.api;

import io.micronaut.serde.annotation.Serdeable;
import lombok.Value;
import perobobbot.api.data.Platform;

import java.util.Map;

@Value
@Serdeable
public class SubscriptionView {
    long id;
    Platform platform;
    String subscriptionType;
    Map<String,String> conditions;
    /**
     * Id of the subscription on the platform
     */
    String subscriptionId;

    boolean enabled;
    /**
     * The url used for this subscription
     */
    String callbackUrl;

    public boolean hasPlatformId() {
        return !subscriptionId.isEmpty();
    }

}
