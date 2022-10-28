package perobobbot.chat.api;

import lombok.NonNull;

public interface ChatMessageDispatcher {

    void sendMessage(@NonNull ChatMessage message);
}
