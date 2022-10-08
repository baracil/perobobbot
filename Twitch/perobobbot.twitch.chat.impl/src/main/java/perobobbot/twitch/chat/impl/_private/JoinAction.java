package perobobbot.twitch.chat.impl._private;

import fpc.tools.advanced.chat.AdvancedIO;
import fpc.tools.advanced.chat.ReceiptSlip;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.api.data.JoinedChannel;
import perobobbot.twitch.chat.message.from.UserState;
import perobobbot.twitch.chat.message.to.MessageToTwitch;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor
public class JoinAction implements ChatAction {

    private final @NonNull Set<JoinedChannel> joinedChannels;

    private final @NonNull JoinedChannel channelToJoin;


    @Override
    public @NonNull CompletionStage<AdvancedIO> execute(@NonNull AdvancedIO io) {
        return io.sendRequest(MessageToTwitch.join(channelToJoin.channelName()))
                 .whenComplete(this::handleResult)
                 .thenCompose(r -> this.sendHelloMessage(io));
    }

    private void handleResult(ReceiptSlip<UserState> receipt, Throwable throwable) {
        if (receipt != null) {
            joinedChannels.add(channelToJoin);
        }
    }

    private CompletionStage<AdvancedIO> sendHelloMessage(@NonNull AdvancedIO io) {
        if (channelToJoin.readOnly()) {
            return CompletableFuture.completedFuture(io);
        }
        return io.sendCommand(MessageToTwitch.privateMsg(channelToJoin.channelName(), "Hello"))
                 .thenApply(r -> io);
    }


}
