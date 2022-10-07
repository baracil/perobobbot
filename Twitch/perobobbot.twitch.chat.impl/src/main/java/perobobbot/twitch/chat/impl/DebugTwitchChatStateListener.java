package perobobbot.twitch.chat.impl;

import fpc.tools.advanced.chat.AdvancedChat;
import fpc.tools.state.chat.ChatStateListener;
import fpc.tools.state.chat.OnConnectedResult;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.twitch.chat.message.from.MessageFromTwitch;

@RequiredArgsConstructor
public class DebugTwitchChatStateListener implements ChatStateListener<MessageFromTwitch> {

    private final @NonNull ChatStateListener<MessageFromTwitch> listener;

    @Override
    public void onRetry() {
        System.out.println("DebugTwitchChatStateListener.onRetry");
        listener.onRetry();
    }

    @Override
    public void onConnectionStarted() {
        System.out.println("DebugTwitchChatStateListener.onConnectionStarted");
        listener.onConnectionStarted();
    }

    @Override
    public @NonNull OnConnectedResult onConnected(@NonNull AdvancedChat<MessageFromTwitch> chat, int nbTries) throws InterruptedException {
        System.out.println("DebugTwitchChatStateListener.onConnected");
        return listener.onConnected(chat,nbTries);
    }

    @Override
    public void onConnectionFailed(@NonNull RuntimeException error) {
        System.out.println("DebugTwitchChatStateListener.onConnectionFailed");
        listener.onConnectionFailed(error);
    }


    @Override
    public void onDisconnected() {
        System.out.println("DebugTwitchChatStateListener.onDisconnected");
        listener.onDisconnected();
    }

}
