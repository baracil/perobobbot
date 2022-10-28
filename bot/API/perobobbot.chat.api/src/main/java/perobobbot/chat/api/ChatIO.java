package perobobbot.chat.api;

import lombok.NonNull;
import perobobbot.api.data.Platform;

import java.util.concurrent.CompletionStage;

public interface ChatIO {

    @NonNull Platform getPlatform();

    @NonNull
    CompletionStage<DispatchSlip> sendMessage(@NonNull String channel, @NonNull String message);

}
