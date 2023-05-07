package perobobbot.twitch.chat.impl._private;

import fpc.tools.advanced.chat.AdvancedIO;
import fpc.tools.advanced.chat.ReceiptSlip;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import perobobbot.api.data.JoinedChannel;
import perobobbot.twitch.chat.message.from.UserState;
import perobobbot.twitch.chat.message.to.MessageToTwitch;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor
@Slf4j
public class JoinAction implements ChatAction {

    private final String userId;
    private final Set<JoinedChannel> joinedChannels;
    private final JoinedChannel channelToJoin;


    @Override
    public CompletionStage<AdvancedIO> execute(AdvancedIO io) {
        LOG.info("{} : join channel {}", userId, channelToJoin.channelName());
        return io.sendRequest(MessageToTwitch.join(channelToJoin.channelName()))
                 .whenComplete(this::handleResult)
                 .thenCompose(r -> this.sendHelloMessage(io));
    }

    private void handleResult(ReceiptSlip<UserState> receipt, Throwable throwable) {
        if (receipt != null) {
            joinedChannels.add(channelToJoin);
        }
    }

    private CompletionStage<AdvancedIO> sendHelloMessage(AdvancedIO io) {
        if (true || channelToJoin.readOnly()) {
            return CompletableFuture.completedFuture(io);
        }
        return io.sendCommand(MessageToTwitch.privateMsg(channelToJoin.channelName(), "Hello"))
                 .thenApply(r -> io);
    }


}
