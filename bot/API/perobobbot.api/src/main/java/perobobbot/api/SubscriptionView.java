package perobobbot.api;

import com.google.common.collect.ImmutableMap;
import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;
import lombok.Value;
import perobobbot.api.data.Platform;

@Value
@Serdeable
public class SubscriptionView {
    long id;
    @NonNull Platform platform;
    @NonNull String subscriptionType;
    @NonNull ImmutableMap<String,String> conditions;
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
