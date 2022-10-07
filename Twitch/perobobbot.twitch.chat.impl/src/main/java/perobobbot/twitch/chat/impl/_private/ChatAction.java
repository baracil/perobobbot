package perobobbot.twitch.chat.impl._private;

import fpc.tools.advanced.chat.AdvancedIO;
import lombok.NonNull;
import perobobbot.twitch.chat.message.from.MessageFromTwitch;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public interface ChatAction {

    ChatAction NOP = CompletableFuture::completedFuture;

    @NonNull CompletionStage<? extends AdvancedIO<MessageFromTwitch>> execute(@NonNull AdvancedIO<MessageFromTwitch> io);

    default @NonNull ChatAction accumulate(@NonNull ChatAction other) {
        return io -> this.execute(io).thenCompose(other::execute);
    }

}
