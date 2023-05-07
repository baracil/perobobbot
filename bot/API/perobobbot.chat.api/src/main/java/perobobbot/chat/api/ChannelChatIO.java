package perobobbot.chat.api;

import perobobbot.api.data.Platform;

import java.util.concurrent.CompletionStage;

public interface ChannelChatIO {

    Platform getPlatform();

    String getChannelName();

    CompletionStage<DispatchSlip> sendMessage(String message);

}
