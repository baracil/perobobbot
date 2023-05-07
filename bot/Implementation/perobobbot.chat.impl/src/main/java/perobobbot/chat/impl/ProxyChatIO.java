package perobobbot.chat.impl;

import lombok.RequiredArgsConstructor;
import perobobbot.api.data.Platform;
import perobobbot.chat.api.ChatIO;
import perobobbot.chat.api.DispatchSlip;

import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor
public class ProxyChatIO implements ChatIO {

    private final ChatIO delegate;

    @Override
    public Platform getPlatform() {
        return delegate.getPlatform();
    }

    @Override
    public CompletionStage<DispatchSlip> sendMessage(String channel, String message) {
        return delegate.sendMessage(channel, message);
    }

}
