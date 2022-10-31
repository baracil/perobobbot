package perobobbot.chat.impl;

import com.google.common.collect.ImmutableList;
import fpc.tools.lang.Listeners;
import fpc.tools.lang.Subscription;
import io.micronaut.retry.annotation.Fallback;
import jakarta.inject.Singleton;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import perobobbot.api.plugin.PerobobbotService;
import perobobbot.chat.api.ChatMessage;
import perobobbot.chat.api.ChatMessageDispatcher;
import perobobbot.chat.api.ChatMessageListener;

import java.util.List;

@Singleton
@Fallback
@Slf4j
@PerobobbotService(serviceType = ChatMessageDispatcher.class,apiVersion = ChatMessageDispatcher.VERSION)
public class SimpleChatMessageDispatcher implements ChatMessageDispatcher {

    private final @NonNull Listeners<ChatMessageListener> listeners;

    public SimpleChatMessageDispatcher(@NonNull List<ChatMessageListener> listeners) {
        this.listeners = Listeners.create(ImmutableList.copyOf(listeners));
    }

    @Override
    public @NonNull Subscription addListener(@NonNull ChatMessageListener listener) {
        return listeners.addListener(listener);
    }

    @Override
    public void sendMessage(@NonNull ChatMessage message) {
        if (listeners.isEmpty()) {
            LOG.debug("No listener to chat message : {}", message);
        } else {
            listeners.forEachListeners(ChatMessageListener::onMessage, message);
        }
    }
}
