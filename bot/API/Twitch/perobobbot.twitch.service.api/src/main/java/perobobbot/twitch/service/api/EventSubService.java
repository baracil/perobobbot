package perobobbot.twitch.service.api;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import perobobbot.twitch.api.eventsub.TwitchSubscription;
import perobobbot.twitch.api.eventsub.subscription.Subscription;

public interface EventSubService {

    @AppAuth
    @NonNull TwitchSubscription createSubscription(@NonNull Subscription subscription);

    @AppAuth
    @NonNull ImmutableList<TwitchSubscription> getSubscriptions();

    @AppAuth
    void deleteSubscription(@NonNull String subscriptionId);
}
