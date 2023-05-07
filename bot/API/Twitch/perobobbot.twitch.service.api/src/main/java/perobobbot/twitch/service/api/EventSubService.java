package perobobbot.twitch.service.api;

import perobobbot.twitch.api.eventsub.TwitchSubscription;
import perobobbot.twitch.api.eventsub.subscription.Subscription;

import java.util.List;

public interface EventSubService {

    @AppAuth
    TwitchSubscription createSubscription(Subscription subscription);

    @AppAuth
    List<TwitchSubscription> getSubscriptions();

    @AppAuth
    void deleteSubscription(String subscriptionId);
}
