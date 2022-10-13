package perobobbot.chat.impl;

import io.micronaut.retry.annotation.Fallback;
import jakarta.inject.Singleton;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import perobobbot.chat.api.ChatMessage;
import perobobbot.chat.api.ChatMessageDispatcher;

@Singleton
@Fallback
@Slf4j
public class FallbackChatMessageDispatcher implements ChatMessageDispatcher {

    public FallbackChatMessageDispatcher() {
        LOG.warn("Fallback ChatMessageDispatcher. You should add a bean of your own implementation of ChatMessageDispatcher");
    }

    @Override
    public void sendMessage(@NonNull ChatMessage message) {
        System.out.println(message);
    }
}
