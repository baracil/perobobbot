package perobobbot.twitch.chat;

import fpc.tools.advanced.chat.DispatchSlip;
import fpc.tools.advanced.chat.ReceiptSlip;
import fpc.tools.lang.Subscription;
import perobobbot.twitch.chat.message.to.CommandToTwitch;
import perobobbot.twitch.chat.message.to.RequestToTwitch;

import java.util.concurrent.CompletionStage;

public interface TwitchIO {

    CompletionStage<DispatchSlip> sendCommand(CommandToTwitch command);

    <A> CompletionStage<ReceiptSlip<A>> sendRequest(RequestToTwitch<A> request);

    Subscription addChatListener(TwitchChatListener listener);

    boolean isRunning();


}
