package perobobbot.twitch.chat.impl._private;

import fpc.tools.advanced.chat.AdvancedIO;
import fpc.tools.advanced.chat.ReceiptSlip;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.api.data.Channel;
import perobobbot.twitch.chat.message.from.MessageFromTwitch;
import perobobbot.twitch.chat.message.from.Part;
import perobobbot.twitch.chat.message.to.MessageToTwitch;

import java.util.Set;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

@RequiredArgsConstructor
public class PartAction implements ChatAction {
    private final @NonNull Set<Channel> joinedChannels;
    private final @NonNull Channel channel;

    @Override
    public @NonNull CompletionStage<AdvancedIO<MessageFromTwitch>> execute(@NonNull AdvancedIO<MessageFromTwitch> io) {
        return io.sendRequest(MessageToTwitch.part(channel))
                 .whenComplete(this::handleResult)
                 .thenApply(Function.identity());
    }

    private void handleResult(ReceiptSlip<Part,MessageFromTwitch> part, Throwable throwable) {
        if (part != null) {
            joinedChannels.remove(channel);
        }
    }



}
