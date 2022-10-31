package perobobbot.chat.api;

import lombok.NonNull;

public interface ChatMessageListener {

    void onMessage(@NonNull ChatMessage chatMessage);
}
