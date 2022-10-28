package perobobbot.twitch.chat;

import fpc.tools.advanced.chat.DispatchSlip;
import fpc.tools.advanced.chat.ReceiptSlip;
import fpc.tools.lang.Subscription;
import lombok.NonNull;
import perobobbot.twitch.chat.message.to.CommandToTwitch;
import perobobbot.twitch.chat.message.to.RequestToTwitch;

import java.util.concurrent.CompletionStage;

public interface TwitchIO {

    @NonNull CompletionStage<DispatchSlip> sendCommand(@NonNull CommandToTwitch command);

    @NonNull <A> CompletionStage<ReceiptSlip<A>> sendRequest(@NonNull RequestToTwitch<A> request);

    @NonNull Subscription addChatListener(@NonNull TwitchChatListener listener);

    boolean isRunning();


}
