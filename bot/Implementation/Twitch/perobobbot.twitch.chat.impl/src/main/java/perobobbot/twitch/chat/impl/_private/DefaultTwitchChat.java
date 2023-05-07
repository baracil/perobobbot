package perobobbot.twitch.chat.impl._private;

import fpc.tools.advanced.chat.DispatchSlip;
import fpc.tools.advanced.chat.ReceiptSlip;
import fpc.tools.lang.Instants;
import fpc.tools.lang.Looper;
import fpc.tools.lang.Subscription;
import fpc.tools.lang.ThrowableTool;
import lombok.Synchronized;
import perobobbot.api.Identity;
import perobobbot.api.JoinedChannelProviderForUser;
import perobobbot.api.data.UserIdentity;
import perobobbot.api.data.UserIdentityProvider;
import perobobbot.bus.api.Bus;
import perobobbot.chat.api.ChatMessage;
import perobobbot.chat.api.GenericChatMessage;
import perobobbot.chat.api.PrivateChatMessage;
import perobobbot.chat.api.irc.MessageData;
import perobobbot.oauth.api.OAuthData;
import perobobbot.twitch.api.Twitch;
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

    private final UserIdentity bot;
    private final TwitchChatStateListener twitchChatStateListener;
    private final TwitchChatStateManager twitchChatStateManager;
    private final Looper channelJoiner;
    private final Bus bus;
    private final UserIdentityProvider userIdentityProvider;


    public DefaultTwitchChat(UserIdentity bot,
                             OAuthData oAuthData,
                             JoinedChannelProviderForUser channelProvider,
                             Instants instants,
                             Bus bus,
                             UserIdentityProvider userIdentityProvider) {
        this.bot = bot;
        this.twitchChatStateListener = new TwitchChatStateListener(oAuthData);
        this.twitchChatStateManager = new TwitchChatStateManager(oAuthData.getLogin(), twitchChatStateListener, instants);
        this.channelJoiner = Looper.scheduled(new ChannelJoiner(bot.name(), channelProvider, twitchChatStateManager), Duration.ofSeconds(1), Duration.ofSeconds(10));
        this.twitchChatStateListener.addChatListener(this);
        this.bus = bus;
        this.userIdentityProvider = userIdentityProvider;
    }

    @Override
    @Synchronized
    public CompletionStage<TwitchChat> requestConnection() {
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
    public CompletionStage<?> requestDisconnection() {
        return twitchChatStateManager.disconnect();
    }


    @Override
    public CompletionStage<DispatchSlip> sendCommand(CommandToTwitch command) {
        return twitchChatStateManager.sendCommand(command);
    }

    @Override
    public <A> CompletionStage<ReceiptSlip<A>> sendRequest(RequestToTwitch<A> request) {
        return twitchChatStateManager.sendRequest(request);
    }

    @Override
    public Subscription addChatListener(TwitchChatListener listener) {
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
    public void onPostedMessage(Instant postInstant, MessageToTwitch message) {
    }

    @Override
    public void onReceivedMessage(Instant receptionInstant, MessageFromTwitch message) {
        final var payload = MessageData.fromFpc(message.getIrcParsing());
        final ChatMessage chatMessage;
        final String channelName;

        if (message instanceof PrivMsgFromTwitch privMsgFromTwitch) {
            final var identity = new Identity(Twitch.PLATFORM, privMsgFromTwitch.getUser());
            final var user = userIdentityProvider.getUserIdentity(identity);
            chatMessage = new PrivateChatMessage(receptionInstant, bot, payload, message, privMsgFromTwitch.getChannelName(), user, privMsgFromTwitch.getPayload());
            channelName = privMsgFromTwitch.getChannelName();
        } else {
            chatMessage = new GenericChatMessage(bot, payload, message);
            channelName = "$irc";
        }

        bus.send("chat:twitch/"+channelName,chatMessage);
    }
}
