package perobobbot.chat.api;

import lombok.NonNull;
import perobobbot.api.data.Platform;
import perobobbot.chat.api._private.SimpleChannelChatIO;

import java.util.concurrent.CompletionStage;

public interface ChatIO {

    @NonNull Platform getPlatform();

    @NonNull
    CompletionStage<DispatchSlip> sendMessage(@NonNull String channel, @NonNull String message);

    default @NonNull ChannelChatIO forChannel(@NonNull String channel) {
        return new SimpleChannelChatIO(this,channel);
    }

}
