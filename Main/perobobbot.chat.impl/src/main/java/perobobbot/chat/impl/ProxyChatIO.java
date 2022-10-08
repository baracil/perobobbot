package perobobbot.chat.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.api.data.Platform;
import perobobbot.chat.api.ChatIO;
import perobobbot.chat.api.DispatchSlip;

import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor
public class ProxyChatIO implements ChatIO {

    private final @NonNull ChatIO delegate;

    @Override
    public @NonNull Platform getPlatform() {
        return delegate.getPlatform();
    }

    @Override
    public @NonNull CompletionStage<DispatchSlip> sendMessage(@NonNull String channel, @NonNull String message) {
        return delegate.sendMessage(channel, message);
    }

}
