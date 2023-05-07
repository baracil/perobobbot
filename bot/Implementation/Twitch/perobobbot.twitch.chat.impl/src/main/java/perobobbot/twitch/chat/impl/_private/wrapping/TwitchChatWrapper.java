package perobobbot.twitch.chat.impl._private.wrapping;

import lombok.RequiredArgsConstructor;
import perobobbot.api.data.Platform;
import perobobbot.chat.api.Chat;
import perobobbot.chat.api.DispatchSlip;
import perobobbot.twitch.api.Twitch;
import perobobbot.twitch.chat.TwitchChat;
import perobobbot.twitch.chat.message.to.MessageToTwitch;

import java.util.concurrent.CompletionStage;

/**
 * Wrap a {@link TwitchChat} to a simple {@link Chat}
 */
@RequiredArgsConstructor
public class TwitchChatWrapper implements Chat {

    private final TwitchChat twitchChat;

    @Override
    public CompletionStage<Chat> requestConnection() {
        return twitchChat.requestConnection().thenApply(TwitchChatWrapper::new);
    }

    @Override
    public void connect() throws InterruptedException {
        twitchChat.connect();
    }

    @Override
    public CompletionStage<?> requestDisconnection() {
        return twitchChat.requestDisconnection();
    }

    @Override
    public Platform getPlatform() {
        return Twitch.PLATFORM;
    }

    @Override
    public CompletionStage<DispatchSlip> sendMessage(String channel, String message) {
        return twitchChat.sendCommand(MessageToTwitch.privateMsg(channel,message))
                         .thenApply(DispatchSlipWrapper::new);
    }

}
