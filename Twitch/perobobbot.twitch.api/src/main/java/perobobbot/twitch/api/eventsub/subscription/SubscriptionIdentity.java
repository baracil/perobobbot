package perobobbot.twitch.api.eventsub.subscription;

import lombok.NonNull;
import perobobbot.api.data.Platform;
import perobobbot.twitch.api.Conditions;

public interface SubscriptionIdentity {

    @NonNull String getSubscriptionId();

    @NonNull Platform getPlatform();
    @NonNull String getSubscriptionType();
    @NonNull Conditions getConditions();
    @NonNull String getCallbackUrl();

    boolean isValid();

    default @NonNull SubscriptionData createData() {
        return new SubscriptionData(getPlatform(),getSubscriptionType(),getConditions());
    }
}
