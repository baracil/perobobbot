package perobobbot.launcher.eventsub;

import com.google.common.collect.ImmutableList;
import fpc.tools.fp.Nil;
import fpc.tools.lang.Listeners;
import fpc.tools.lang.Subscription;
import io.micronaut.retry.annotation.Fallback;
import jakarta.inject.Singleton;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import perobobbot.api.plugin.PerobobbotService;
import perobobbot.twitch.api.eventsub.*;

import java.util.List;

@Slf4j
@Singleton
@Fallback
@PerobobbotService(serviceType = EventSubRequestDispatcher.class, apiVersion = EventSubRequestDispatcher.VERSION)
public class SimpleEventSubRequestDispatcher implements EventSubRequestDispatcher, EventSubRequest.Visitor<Nil> {

    private final @NonNull Listeners<EventSubListener> listeners;

    public SimpleEventSubRequestDispatcher(@NonNull List<EventSubListener> listeners) {
        this.listeners = Listeners.create(ImmutableList.copyOf(listeners));
    }

    @Override
    public void dispatchEventSub(@NonNull EventSubRequest eventSubRequest) {
        if (listeners.isEmpty()) {
            LOG.warn("No listener to event sub : {}", eventSubRequest);
        } else {
            eventSubRequest.accept(this);
        }
    }

    @Override
    public @NonNull Subscription addListener(@NonNull EventSubListener listener) {
        return listeners.addListener(listener);
    }

    @Override
    public @NonNull Nil visit(@NonNull EventSubNotification notification) {
        listeners.forEachListeners(EventSubListener::handleNotification, notification);
        return Nil.NULL;
    }

    @Override
    public @NonNull Nil visit(@NonNull EventSubRevocation revocation) {
        listeners.forEachListeners(EventSubListener::handleRevocation, revocation);
        return Nil.NULL;
    }

    @Override
    public @NonNull Nil visit(@NonNull EventSubVerification verification) {
        listeners.forEachListeners(EventSubListener::handleVerification, verification);
        return Nil.NULL;
    }
}
