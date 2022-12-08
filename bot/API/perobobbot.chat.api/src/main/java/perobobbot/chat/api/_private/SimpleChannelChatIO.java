package perobobbot.chat.api._private;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.api.data.Platform;
import perobobbot.chat.api.ChannelChatIO;
import perobobbot.chat.api.ChatIO;
import perobobbot.chat.api.DispatchSlip;

import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor
@Getter
public class SimpleChannelChatIO implements ChannelChatIO {

    private final @NonNull ChatIO chatIO;
    private final @NonNull String channelName;

    @Override
    public @NonNull CompletionStage<DispatchSlip> sendMessage(@NonNull String message) {
        return chatIO.sendMessage(channelName,message);
    }

    @Override
    public @NonNull Platform getPlatform() {
        return chatIO.getPlatform();
    }
}
