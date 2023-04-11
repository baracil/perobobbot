package perobobbot.api;

import lombok.NonNull;
import lombok.Value;
import perobobbot.api.data.Platform;

import java.util.Map;

@Value
public class SubscriptionData {

    @NonNull Platform platform;
    @NonNull String subscriptionType;
    @NonNull Map<String,String> conditions;

    public SubscriptionData(@NonNull Platform platform, @NonNull String subscriptionType, @NonNull Map<String,String> conditions) {
        this.platform = platform;
        this.subscriptionType = subscriptionType;
        this.conditions = conditions;
    }

}
