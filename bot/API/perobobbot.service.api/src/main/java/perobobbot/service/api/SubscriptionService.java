package perobobbot.service.api;

import lombok.NonNull;
import perobobbot.api.SubscriptionView;
import perobobbot.api.data.Platform;

import java.util.List;
import java.util.Optional;

public interface SubscriptionService {

    @NonNull List<SubscriptionView> listSubscriptionsOnPlatform(@NonNull Platform platform, int page, int size);

    @NonNull List<SubscriptionView> listSubscriptions(int page, int size);

    @NonNull Optional<SubscriptionView> deleteSubscription(long id);

    @NonNull SubscriptionView updateSubscription(long id, @NonNull UpdateSubscriptionParameters parameters);

    @NonNull SubscriptionView createSubscription(@NonNull CreateSubscriptionParameters parameters);

    @NonNull SubscriptionView getSubscription(long id);

    @NonNull SubscriptionView patchSubscription(long id, @NonNull PatchSubscriptionParameters parameters);

    default @NonNull List<SubscriptionView> listSubscriptionsOnPlatform(@NonNull Platform platform) {
        return this.listSubscriptionsOnPlatform(platform, 0, -1);
    }

    default @NonNull List<SubscriptionView> listSubscriptions() {
        return this.listSubscriptions(0,-1);
    }
}
