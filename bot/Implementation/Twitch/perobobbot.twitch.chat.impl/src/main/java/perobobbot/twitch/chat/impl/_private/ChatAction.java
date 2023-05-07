package perobobbot.twitch.chat.impl._private;

import fpc.tools.advanced.chat.AdvancedIO;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public interface ChatAction {

    ChatAction NOP = CompletableFuture::completedFuture;

    CompletionStage<? extends AdvancedIO> execute(AdvancedIO io);

    default ChatAction accumulate(ChatAction other) {
        return io -> this.execute(io).thenCompose(other::execute);
    }

}
