package perobobbot.chat.api;

import perobobbot.api.data.Platform;
import perobobbot.chat.api._private.SimpleChannelChatIO;

import java.util.concurrent.CompletionStage;

public interface ChatIO {

    Platform getPlatform();

    CompletionStage<DispatchSlip> sendMessage(String channel, String message);

    default ChannelChatIO forChannel(String channel) {
        return new SimpleChannelChatIO(this,channel);
    }

}
