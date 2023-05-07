package perobobbot.api;

import lombok.Value;
import perobobbot.api.data.Platform;

import java.util.Map;

@Value
public class SubscriptionData {

    Platform platform;
    String subscriptionType;
    Map<String,String> conditions;

    public SubscriptionData(Platform platform, String subscriptionType, Map<String,String> conditions) {
        this.platform = platform;
        this.subscriptionType = subscriptionType;
        this.conditions = conditions;
    }

}
