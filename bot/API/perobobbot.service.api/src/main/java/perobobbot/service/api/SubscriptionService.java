package perobobbot.service.api;

import perobobbot.api.SubscriptionView;
import perobobbot.api.data.Platform;

import java.util.List;
import java.util.Optional;

public interface SubscriptionService {

    List<SubscriptionView> listSubscriptionsOnPlatform(Platform platform, int page, int size);

    List<SubscriptionView> listSubscriptions(int page, int size);

    Optional<SubscriptionView> deleteSubscription(long id);

    SubscriptionView updateSubscription(long id, UpdateSubscriptionParameters parameters);

    SubscriptionView createSubscription(CreateSubscriptionParameters parameters);

    SubscriptionView getSubscription(long id);

    SubscriptionView patchSubscription(long id, PatchSubscriptionParameters parameters);

    default List<SubscriptionView> listSubscriptionsOnPlatform(Platform platform) {
        return this.listSubscriptionsOnPlatform(platform, 0, -1);
    }

    default List<SubscriptionView> listSubscriptions() {
        return this.listSubscriptions(0,-1);
    }
}
