package perobobbot.twitch.api.eventsub.subscription;

import perobobbot.twitch.api.Conditions;

public interface SubscriptionFactory {

    Subscription create(Conditions conditions);

}
