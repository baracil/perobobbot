package perobobbot.service.api;

import lombok.NonNull;
import lombok.Value;

@Value
public class UpdateSubscriptionParameters {

    @NonNull String subscriptionId;
    @NonNull String callbackUrl;

}
