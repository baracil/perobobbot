package perobobbot.twitch.chat.impl._private;

import fpc.tools.advanced.chat.DispatchSlip;
import fpc.tools.advanced.chat.ReceiptSlip;
import fpc.tools.lang.Instants;
import fpc.tools.lang.Looper;
import fpc.tools.lang.Subscription;
import fpc.tools.lang.ThrowableTool;
import lombok.NonNull;
import lombok.Synchronized;
import perobobbot.api.Identity;
import perobobbot.api.JoinedChannelProviderForUser;
import perobobbot.api.bus.Bus;
import perobobbot.api.data.UserIdentity;
import perobobbot.chat.api.ChatMessage;
import perobobbot.chat.api.irc.MessageData;
import perobobbot.oauth.api.OAuthData;
import perobobbot.twitch.chat.TwitchChat;
import perobobbot.twitch.chat.TwitchChatListener;
import perobobbot.twitch.chat.message.from.MessageFromTwitch;
import perobobbot.twitch.chat.message.from.PrivMsgFromTwitch;
import perobobbot.twitch.chat.message.to.CommandToTwitch;
import perobobbot.twitch.chat.message.to.MessageToTwitch;
import perobobbot.twitch.chat.message.to.RequestToTwitch;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class DefaultTwitchChat implements TwitchChat, TwitchChatListener {

    private final @NonNull Identity bot;
    private final @NonNull TwitchChatStateListener twitchChatStateListener;
    private final @NonNull TwitchChatStateManager twitchChatStateManager;
    private final @NonNull Looper channelJoiner;
    private final @NonNull Bus bus;


    public DefaultTwitchChat(@NonNull UserIdentity bot,
                             @NonNull OAuthData oAuthData,
                             @NonNull JoinedChannelProviderForUser channelProvider,
                             @NonNull Instants instants,
                             @NonNull Bus bus) {
        this.bot = bot.identity();
        this.twitchChatStateListener = new TwitchChatStateListener(oAuthData);
        this.twitchChatStateManager = new TwitchChatStateManager(oAuthData.getLogin(), twitchChatStateListener, instants);
        this.channelJoiner = Looper.scheduled(new ChannelJoiner(bot.name(), channelProvider, twitchChatStateManager), Duration.ofSeconds(1), Duration.ofSeconds(10));
        this.twitchChatStateListener.addChatListener(this);
        this.bus = bus;
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
    public @NonNull CompletionStage<?> requestDisconnection() {
        return twitchChatStateManager.disconnect();
    }


    @Override
    public @NonNull CompletionStage<DispatchSlip> sendCommand(@NonNull CommandToTwitch command) {
        return twitchChatStateManager.sendCommand(command);
    }

    @Override
    public @NonNull <A> CompletionStage<ReceiptSlip<A>> sendRequest(@NonNull RequestToTwitch<A> request) {
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
        final var payload = MessageData.fromFpc(message.getIrcParsing());
        final String privateMessage;
        if (message instanceof PrivMsgFromTwitch privMsgFromTwitch) {
            privateMessage = privMsgFromTwitch.getPayload();
        } else {
            privateMessage = null;
        }
        final var chatMessage = new ChatMessage(bot, payload, message, privateMessage);
        bus.publishEvent(chatMessage);
    }
}
