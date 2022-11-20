package perobobbot.launcher;

import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.discovery.event.ServiceReadyEvent;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.service.api.CreateSubscriptionParameters;
import perobobbot.service.api.SubscriptionService;
import perobobbot.twitch.api.Conditions;
import perobobbot.twitch.api.CriteriaType;
import perobobbot.twitch.api.SubscriptionType;
import perobobbot.twitch.api.Twitch;
import perobobbot.twitch.api.eventsub.TransportMethod;
import perobobbot.twitch.api.eventsub.TransportRequest;
import perobobbot.twitch.api.eventsub.TwitchSubscriptionRequest;

@RequiredArgsConstructor
//@Singleton
public class Setup implements ApplicationEventListener<ServiceReadyEvent> {

    private final @NonNull SubscriptionService subscriptionService;

    @Override
    public void onApplicationEvent(ServiceReadyEvent event) {
        final var conditions = Conditions.builder()
                                         .put(CriteriaType.BROADCASTER_USER_ID, "211307900")
                                         .put(CriteriaType.TO_BROADCASTER_USER_ID, "211307900")
                                         .put(CriteriaType.CLIENT_ID, "m01e1fb0emhtr0toc6eydvl9zkuecu")
                                         .put(CriteriaType.USER_ID, "211307900")
                                         .build();
        for (SubscriptionType value : SubscriptionType.values()) {
            if (value == SubscriptionType.DROP_ENTITLEMENT_GRANT
                    || value == SubscriptionType.EXTENSION_BITS_TRANSACTION_CREATE
            || value == SubscriptionType.USER_AUTHORIZATION_GRANT || value == SubscriptionType.USER_AUTHORIZATION_REVOKE) {
                continue;
            }
            final var a = value.create(conditions)
                               .completeRequest(TwitchSubscriptionRequest.builder())
                               .transport(new TransportRequest(TransportMethod.WEBHOOK, "asd", "asd"))
                               .build();
            subscriptionService.createSubscription(new CreateSubscriptionParameters(Twitch.PLATFORM, value.getIdentification(), a.getCondition().toMap(),false));
        }
    }
}
