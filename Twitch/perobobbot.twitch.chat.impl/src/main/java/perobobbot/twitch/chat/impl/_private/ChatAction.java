package perobobbot.twitch.chat.impl._private;

import fpc.tools.advanced.chat.AdvancedIO;
import lombok.NonNull;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public interface ChatAction {

    ChatAction NOP = CompletableFuture::completedFuture;

    @NonNull CompletionStage<? extends AdvancedIO> execute(@NonNull AdvancedIO io);

    default @NonNull ChatAction accumulate(@NonNull ChatAction other) {
        return io -> this.execute(io).thenCompose(other::execute);
    }

}
