package perobobbot.twitch.api.eventsub.subscription;

import lombok.NonNull;
import perobobbot.twitch.api.Conditions;

public interface SubscriptionFactory {

    @NonNull Subscription create(@NonNull Conditions conditions);

}
