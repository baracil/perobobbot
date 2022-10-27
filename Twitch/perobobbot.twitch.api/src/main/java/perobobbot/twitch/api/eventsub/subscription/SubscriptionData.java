package perobobbot.twitch.api.eventsub.subscription;

import fpc.tools.lang.IdentifiedEnum;
import lombok.NonNull;
import lombok.Value;
import perobobbot.api.data.Platform;
import perobobbot.twitch.api.Conditions;

@Value
public class SubscriptionData {

    @NonNull Platform platform;
    @NonNull String subscriptionType;
    @NonNull Conditions conditions;

    public SubscriptionData(@NonNull Platform platform, @NonNull String subscriptionType, @NonNull Conditions conditions) {
        this.platform = platform;
        this.subscriptionType = subscriptionType;
        this.conditions = conditions;
    }

    public SubscriptionData(@NonNull Platform platform, @NonNull IdentifiedEnum subscriptionType, @NonNull Conditions conditions) {
        this(platform,subscriptionType.getIdentification(),conditions);
    }
}
