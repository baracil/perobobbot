package perobobbot.twitch.api.eventsub;

import fpc.tools.fp.Nil;
import lombok.NonNull;
import perobobbot.twitch.api.eventsub.subscription.Subscription;
import perobobbot.twitch.api.eventsub.subscription.UserSubscriptionView;

import java.util.UUID;
import java.util.concurrent.CompletionStage;

public interface EventSubHandler {

    void handleEventSubRequest(@NonNull TwitchRequestContent<EventSubRequest> request);

    @NonNull CompletionStage<Nil> handleSubscriptionDeletion(@NonNull String login, @NonNull UUID subscriptionId);

    @NonNull CompletionStage<UserSubscriptionView> handleCreateSubscription(@NonNull String login, @NonNull Subscription subscription);

}
