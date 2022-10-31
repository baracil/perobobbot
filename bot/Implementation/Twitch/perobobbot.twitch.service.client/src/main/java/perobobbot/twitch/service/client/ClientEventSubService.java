package perobobbot.twitch.service.client;

import com.google.common.collect.ImmutableList;
import jakarta.inject.Singleton;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.oauth.api.AuthHolder;
import perobobbot.twitch.api.Twitch;
import perobobbot.twitch.api.eventsub.*;
import perobobbot.twitch.api.eventsub.subscription.Subscription;
import perobobbot.twitch.service.api.EventSubService;
import perobobbot.twitch.web.client.EventSubClient;
import perobobbot.twitch.web.client.GetEventSubParameter;

@RequiredArgsConstructor
@Singleton
public class ClientEventSubService implements EventSubService {

    private final @NonNull EventSubClient eventSubClient;
    private final @NonNull TwitchEventSubConfiguration configuration;
    private final @NonNull AuthHolder authHolder;


    @Override
    public @NonNull TwitchSubscription createSubscription(@NonNull Subscription subscription) {
        final var request = subscription.completeRequest(TwitchSubscriptionRequest.builder())
                                        .transport(createTransport())
                                        .build();

        final var eventSub = eventSubClient.createEventSub(request);
        return eventSub.getData()[0];
    }

    @Override
    public @NonNull ImmutableList<TwitchSubscription> getSubscriptions() {
        final var oAuthData = authHolder.get(Twitch.PLATFORM);
        final var parameter = GetEventSubParameter.builder().user_id(oAuthData.getUserId()).build();
        return ImmutableList.copyOf(eventSubClient.getEventSubs(parameter).getData());
    }

    @Override
    public void deleteSubscription(@NonNull String subscriptionId) {
        eventSubClient.delete(subscriptionId);
    }


    private @NonNull TransportRequest createTransport() {
        return TransportRequest.builder()
                               .method(TransportMethod.WEBHOOK)
                               .callback(configuration.getCallbackUrl())
                               .secret(configuration.getSecret().value())
                               .build();
    }
}
