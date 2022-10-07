package perobobbot.twitch.chat.impl;

import fpc.tools.advanced.chat.DispatchSlip;
import fpc.tools.advanced.chat.ReceiptSlip;
import fpc.tools.lang.Instants;
import fpc.tools.lang.Looper;
import fpc.tools.lang.Subscription;
import fpc.tools.lang.ThrowableTool;
import lombok.NonNull;
import lombok.Synchronized;
import perobobbot.api.ChannelProviderForUser;
import perobobbot.api.oauth.OAuthData;
import perobobbot.twitch.chat.TwitchChat;
import perobobbot.twitch.chat.TwitchChatListener;
import perobobbot.twitch.chat.impl._private.TwitchChatStateListener;
import perobobbot.twitch.chat.impl._private.TwitchChatStateManager;
import perobobbot.twitch.chat.message.from.MessageFromTwitch;
import perobobbot.twitch.chat.message.to.CommandToTwitch;
import perobobbot.twitch.chat.message.to.MessageToTwitch;
import perobobbot.twitch.chat.message.to.RequestToTwitch;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class DefaultTwitchChat implements TwitchChat, TwitchChatListener {

    private final TwitchChatStateListener twitchChatStateListener;
    private final TwitchChatStateManager twitchChatStateManager;
    private final Looper channelJoiner;


    public DefaultTwitchChat(@NonNull OAuthData oAuthData,
                             @NonNull ChannelProviderForUser channelProvider,
                             @NonNull Instants instants) {
        this.twitchChatStateListener = new TwitchChatStateListener(oAuthData);
        this.twitchChatStateManager = new TwitchChatStateManager(oAuthData.getLogin(), twitchChatStateListener, instants);
        this.channelJoiner = Looper.scheduled(new ChannelJoiner(channelProvider, twitchChatStateManager), Duration.ofSeconds(1), Duration.ofSeconds(10));
        this.twitchChatStateListener.addChatListener(this);
    }

    @Override
    @Synchronized
    public @NonNull CompletionStage<TwitchChat> requestConnection() {
        twitchChatStateManager.connect();
        final var future = new CompletableFuture<TwitchChat>();
        new Thread(() -> {
            try {
                twitchChatStateListener.waitForConnectionResult();
                future.complete(this);
            } catch (Throwable e) {
                ThrowableTool.interruptIfCausedByInterruption(e);
                future.completeExceptionally(e);
            }
        }).start();
        return future;
    }

    @Override
    @Synchronized
    public void connect() throws InterruptedException {
        twitchChatStateManager.connect();
        twitchChatStateListener.waitForConnectionResult();
    }

    @Override
    public void requestDisconnection() {
        twitchChatStateManager.disconnect();
    }


    @Override
    public @NonNull CompletionStage<DispatchSlip<MessageFromTwitch>> sendCommand(@NonNull CommandToTwitch command) {
        return twitchChatStateManager.sendCommand(command);
    }

    @Override
    public @NonNull <A> CompletionStage<ReceiptSlip<A, MessageFromTwitch>> sendRequest(@NonNull RequestToTwitch<A> request) {
        return twitchChatStateManager.sendRequest(request);
    }

    @Override
    public @NonNull Subscription addChatListener(@NonNull TwitchChatListener listener) {
        return twitchChatStateListener.addChatListener(listener);
    }

    @Override
    public boolean isRunning() {
        return twitchChatStateListener.isRunning();
    }

    @Override
    public void onConnected() {
        channelJoiner.start();
    }

    @Override
    public void onDisconnected() {
        channelJoiner.requestStop();
    }

    @Override
    public void onPostedMessage(@NonNull Instant postInstant, @NonNull MessageToTwitch message) {
    }

    @Override
    public void onReceivedMessage(@NonNull Instant receptionInstant, @NonNull MessageFromTwitch message) {
    }
}
