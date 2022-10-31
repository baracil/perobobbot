package perobobbot.api;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.Value;
import perobobbot.api.data.Platform;

@Value
public class SubscriptionData {

    @NonNull Platform platform;
    @NonNull String subscriptionType;
    @NonNull ImmutableMap<String,String> conditions;

    public SubscriptionData(@NonNull Platform platform, @NonNull String subscriptionType, @NonNull ImmutableMap<String,String> conditions) {
        this.platform = platform;
        this.subscriptionType = subscriptionType;
        this.conditions = conditions;
    }

}
