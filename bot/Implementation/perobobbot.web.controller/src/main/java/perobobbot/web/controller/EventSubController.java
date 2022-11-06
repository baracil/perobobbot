package perobobbot.web.controller;

import com.google.common.collect.ImmutableList;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.api.SubscriptionManager;
import perobobbot.api.SubscriptionView;
import perobobbot.api.data.Platform;
import perobobbot.service.api.CreateSubscriptionParameters;
import perobobbot.service.api.SubscriptionService;
import perobobbot.web.api.EventSubApi;

@Controller(EventSubApi.PATH)
@RequiredArgsConstructor
@ExecuteOn(TaskExecutors.IO)
public class EventSubController implements EventSubApi {

    private final @NonNull SubscriptionService subscriptionService;
    private final @NonNull SubscriptionManager subscriptionManager;

    @Override
    public @NonNull ImmutableList<SubscriptionView> listEventSubs(@QueryValue(value = "platform") @Nullable Platform platform,
                                                                  @QueryValue(value = "page", defaultValue = "0") int page,
                                                                  @QueryValue(value = "size", defaultValue = "-1") int size) {
        if (platform == null) {
            return subscriptionService.listSubscriptions(page,size);
        }
        return subscriptionService.listSubscriptionsOnPlatform(platform,page,size);
    }

    @Override
    public @NonNull SubscriptionView getEventSubs(@PathVariable long id) {
        return subscriptionService.getSubscription(id);
    }

    @Override
    public void deleteEventSubs(@PathVariable long id) {
        subscriptionService.deleteSubscription(id)
                           .ifPresent(view -> subscriptionManager.requestSynchronization(view.getPlatform()));
    }

    @Override
    public @NonNull SubscriptionView createSubscription(@Body @NonNull CreateSubscriptionParameters parameters) {
        final var subscription = subscriptionService.createSubscription(parameters);
        subscriptionManager.requestSynchronization(subscription.getPlatform());
        return subscription;
    }
}
