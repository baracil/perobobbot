package perobobbot.service.api;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import perobobbot.api.SubscriptionView;
import perobobbot.api.data.Platform;

import java.util.Optional;

public interface SubscriptionService {

    @NonNull ImmutableList<SubscriptionView> listSubscriptionsOnPlatform(@NonNull Platform platform, int page, int size);

    @NonNull ImmutableList<SubscriptionView> listSubscriptions(int page, int size);

    @NonNull Optional<SubscriptionView> deleteSubscription(long id);

    @NonNull SubscriptionView updateSubscription(long id, @NonNull UpdateSubscriptionParameters parameters);

    @NonNull SubscriptionView createSubscription(@NonNull CreateSubscriptionParameters parameters);

    @NonNull SubscriptionView getSubscription(long id);

    @NonNull SubscriptionView patchSubscription(long id, @NonNull PatchSubscriptionParameters parameters);

    default @NonNull ImmutableList<SubscriptionView> listSubscriptionsOnPlatform(@NonNull Platform platform) {
        return this.listSubscriptionsOnPlatform(platform, 0, -1);
    }

    default @NonNull ImmutableList<SubscriptionView> listSubscriptions() {
        return this.listSubscriptions(0,-1);
    }
}
