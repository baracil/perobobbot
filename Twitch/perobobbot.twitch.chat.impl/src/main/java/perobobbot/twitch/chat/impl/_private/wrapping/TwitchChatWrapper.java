package perobobbot.twitch.chat.impl._private.wrapping;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.api.data.Platform;
import perobobbot.chat.api.Chat;
import perobobbot.chat.api.DispatchSlip;
import perobobbot.twitch.api.Twitch;
import perobobbot.twitch.chat.TwitchChat;
import perobobbot.twitch.chat.message.to.MessageToTwitch;

import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor
public class TwitchChatWrapper implements Chat {

    private final @NonNull TwitchChat twitchChat;

    @Override
    public @NonNull CompletionStage<Chat> requestConnection() {
        return twitchChat.requestConnection().thenApply(TwitchChatWrapper::new);
    }

    @Override
    public void connect() throws InterruptedException {
        twitchChat.connect();
    }

    @Override
    public void requestDisconnection() {
        twitchChat.requestDisconnection();
    }

    @Override
    public @NonNull Platform getPlatform() {
        return Twitch.PLATFORM;
    }

    @Override
    public @NonNull CompletionStage<DispatchSlip> sendMessage(@NonNull String channel, @NonNull String message) {
        return twitchChat.sendCommand(MessageToTwitch.privateMsg(channel,message))
                         .thenApply(DispatchSlipWrapper::new);
    }

}
