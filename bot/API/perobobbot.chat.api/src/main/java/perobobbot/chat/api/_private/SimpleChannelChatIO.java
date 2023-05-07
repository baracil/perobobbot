package perobobbot.chat.api._private;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import perobobbot.api.data.Platform;
import perobobbot.chat.api.ChannelChatIO;
import perobobbot.chat.api.ChatIO;
import perobobbot.chat.api.DispatchSlip;

import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor
@Getter
public class SimpleChannelChatIO implements ChannelChatIO {

    private final ChatIO chatIO;
    private final String channelName;

    @Override
    public CompletionStage<DispatchSlip> sendMessage(String message) {
        return chatIO.sendMessage(channelName,message);
    }

    @Override
    public Platform getPlatform() {
        return chatIO.getPlatform();
    }
}
