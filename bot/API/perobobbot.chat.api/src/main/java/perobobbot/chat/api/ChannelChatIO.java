package perobobbot.chat.api;

import lombok.NonNull;
import perobobbot.api.data.Platform;

import java.util.concurrent.CompletionStage;

public interface ChannelChatIO {

    @NonNull Platform getPlatform();

    @NonNull String getChannelName();

    @NonNull
    CompletionStage<DispatchSlip> sendMessage(@NonNull String message);

}
