package perobobbot.twitch.eventsub;

import fpc.tools.lang.SubscriptionHolder;
import fpc.tools.micronaut.EagerInit;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Singleton;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.callback.api.CallbackManager;
import perobobbot.twitch.eventsub.callback.EventSubCallback;
import perobobbot.twitch.eventsub.callback.EventSubHandler;

import java.util.List;

@Singleton
@EagerInit
@RequiredArgsConstructor
public class MicronautPlug {
    private final @NonNull CallbackManager callbackManager;
    private final @NonNull List<EventSubHandler> eventSubHandler;
    private final @NonNull SubscriptionHolder subscriptionHolder = new SubscriptionHolder();

    @PostConstruct
    public void registerCallback() {
        subscriptionHolder.replace(() -> callbackManager.addCallback("twitch", new EventSubCallback(eventSubHandler)));
    }

    @PreDestroy
    public void unregisterCallback() {
        subscriptionHolder.unsubscribe();
    }


}
