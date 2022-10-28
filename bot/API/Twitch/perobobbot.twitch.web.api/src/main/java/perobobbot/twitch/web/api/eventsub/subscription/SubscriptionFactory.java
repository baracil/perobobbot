package perobobbot.twitch.web.api.eventsub.subscription;

import lombok.NonNull;
import perobobbot.twitch.web.api.Conditions;

public interface SubscriptionFactory {

    @NonNull Subscription create(@NonNull Conditions conditions);

}
