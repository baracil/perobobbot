package perobobbot.chat.api;

import fpc.tools.lang.Subscription;
import lombok.NonNull;

public interface ChatMessageDispatcher {

    int VERSION = 1;

    void sendMessage(@NonNull ChatMessage message);

    @NonNull Subscription addListener(@NonNull ChatMessageListener listener);
}
