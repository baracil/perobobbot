package perobobbot.service.api;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import perobobbot.api.SubscriptionView;
import perobobbot.api.data.Platform;

public interface SubscriptionService {

    @NonNull ImmutableList<SubscriptionView> listSubscriptions(@NonNull Platform platform);

    void deleteSubscription(long id);

    @NonNull SubscriptionView updateSubscription(long id, @NonNull UpdateSubscriptionParameters parameters);

    @NonNull SubscriptionView createSubscription(@NonNull CreateSubscriptionParameters parameters);
}
