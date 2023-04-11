package perobobbot.twitch.service.api;

import lombok.NonNull;
import perobobbot.twitch.api.eventsub.TwitchSubscription;
import perobobbot.twitch.api.eventsub.subscription.Subscription;

import java.util.List;

public interface EventSubService {

    @AppAuth
    @NonNull TwitchSubscription createSubscription(@NonNull Subscription subscription);

    @AppAuth
    @NonNull List<TwitchSubscription> getSubscriptions();

    @AppAuth
    void deleteSubscription(@NonNull String subscriptionId);
}
