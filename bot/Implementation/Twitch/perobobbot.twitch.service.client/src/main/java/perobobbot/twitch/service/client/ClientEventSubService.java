package perobobbot.twitch.service.client;

import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import perobobbot.oauth.api.AuthHolder;
import perobobbot.twitch.api.Twitch;
import perobobbot.twitch.api.eventsub.*;
import perobobbot.twitch.api.eventsub.subscription.Subscription;
import perobobbot.twitch.service.api.EventSubService;
import perobobbot.twitch.web.client.EventSubClient;
import perobobbot.twitch.web.client.GetEventSubParameter;

import java.util.List;

@RequiredArgsConstructor
@Singleton
public class ClientEventSubService implements EventSubService {

    private final EventSubClient eventSubClient;
    private final TwitchEventSubConfiguration configuration;
    private final AuthHolder authHolder;

    @Override
    public TwitchSubscription createSubscription(Subscription subscription) {
        final var request = subscription.completeRequest(TwitchSubscriptionRequest.builder())
                                        .transport(createTransport())
                                        .build();

        final var eventSub = eventSubClient.createEventSub(request);
        return eventSub.getData()[0];
    }

    @Override
    public List<TwitchSubscription> getSubscriptions() {
        final var oAuthData = authHolder.get(Twitch.PLATFORM);
        final var parameter = GetEventSubParameter.builder().user_id(oAuthData.getUserId()).build();
        return List.of(eventSubClient.getEventSubs(parameter).getData());
    }

    @Override
    public void deleteSubscription(String subscriptionId) {
        eventSubClient.delete(subscriptionId);
    }


    private TransportRequest createTransport() {
        return TransportRequest.builder()
                               .method(TransportMethod.WEBHOOK)
                               .callback(configuration.getCallbackUrl())
                               .secret(configuration.getSecret().value())
                               .build();
    }

}
