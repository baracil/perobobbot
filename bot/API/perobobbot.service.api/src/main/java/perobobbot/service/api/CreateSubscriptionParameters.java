package perobobbot.service.api;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.Value;
import perobobbot.api.data.Platform;

@Value
public class CreateSubscriptionParameters {

    @NonNull Platform platform;
    @NonNull String subscriptionType;
    @NonNull ImmutableMap<String,String> conditions;
    /**
     * The url used for this subscription
     */
    @NonNull String callbackUrl;

}
