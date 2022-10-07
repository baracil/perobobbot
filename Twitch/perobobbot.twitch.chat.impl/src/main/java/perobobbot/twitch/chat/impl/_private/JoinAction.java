package perobobbot.twitch.chat.impl._private;

import fpc.tools.advanced.chat.AdvancedIO;
import fpc.tools.advanced.chat.ReceiptSlip;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.api.data.Channel;
import perobobbot.twitch.chat.message.from.MessageFromTwitch;
import perobobbot.twitch.chat.message.from.UserState;
import perobobbot.twitch.chat.message.to.MessageToTwitch;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

@RequiredArgsConstructor
public class JoinAction implements ChatAction {
    private final @NonNull Set<Channel> joinedChannels;
    private final @NonNull Channel channel;


    @Override
    public @NonNull CompletionStage<AdvancedIO<MessageFromTwitch>> execute(@NonNull AdvancedIO<MessageFromTwitch> io) {
        return io.sendRequest(MessageToTwitch.join(channel))
                 .whenComplete(this::handleResult)
                 .thenCompose(this::sendHelloMessage);
    }

    private void handleResult(ReceiptSlip<UserState, MessageFromTwitch> receipt, Throwable throwable) {
        if (receipt != null) {
            joinedChannels.add(channel);
        }
    }

    private CompletionStage<AdvancedIO<MessageFromTwitch>> sendHelloMessage(ReceiptSlip<UserState, MessageFromTwitch> receiptSlip) {
        if (channel.mute()) {
            return CompletableFuture.completedFuture(receiptSlip);
        }
        return receiptSlip.sendCommand(MessageToTwitch.privateMsg(channel, "Hello"))
                          .thenApply(Function.identity());
    }


}
