package perobobbot.service.api;

import lombok.Value;

@Value
public class UpdateSubscriptionParameters {

    String subscriptionId;
    String callbackUrl;

}
