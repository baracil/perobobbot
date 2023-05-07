package perobobbot.twitch.chat.impl._private;

import fpc.tools.advanced.chat.AdvancedChat;
import fpc.tools.advanced.chat.AdvancedChatListener;
import fpc.tools.advanced.chat.ReceiptSlip;
import fpc.tools.advanced.chat.event.Error;
import fpc.tools.advanced.chat.event.*;
import fpc.tools.cipher.CipherException;
import fpc.tools.fp.Nil;
import fpc.tools.lang.Listeners;
import fpc.tools.lang.Subscription;
import fpc.tools.lang.SubscriptionHolder;
import fpc.tools.state.chat.ChatStateListener;
import fpc.tools.state.chat.OnConnectedResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import perobobbot.oauth.api.OAuthAccessMode;
import perobobbot.oauth.api.OAuthData;
import perobobbot.twitch.chat.TwitchChatAuthenticationFailure;
import perobobbot.twitch.chat.TwitchChatListener;
import perobobbot.twitch.chat.message.Capability;
import perobobbot.twitch.chat.message.from.MessageFromTwitch;
import perobobbot.twitch.chat.message.from.PingFromTwitch;
import perobobbot.twitch.chat.message.to.*;

@RequiredArgsConstructor
@Slf4j
public class TwitchChatStateListener implements ChatStateListener<MessageFromTwitch> {

    private final OAuthData oAuthData;

    private final SubscriptionHolder subscriptionHolder = new SubscriptionHolder();
    private final Listeners<TwitchChatListener> listeners = Listeners.create();
    private final ConnectionResult connectionResult = new ConnectionResult();

    public Subscription addChatListener(TwitchChatListener listener) {
        return listeners.addListener(listener);
    }

    public boolean isRunning() {
        return connectionResult.isRunning();
    }

    @Override
    public void onConnectionStarted() {
        connectionResult.clearConnectionResult();
    }

    @Override
    public void onRetry() {
        subscriptionHolder.unsubscribe();
    }

    @Override
    public void onDisconnected() {
        subscriptionHolder.unsubscribe();
        connectionResult.setOnDisconnection();
    }

    @Override
    public void onConnectionFailed(RuntimeException error) {
        connectionResult.setOnConnectionFailed(error);
    }

    public void waitForConnectionResult() throws InterruptedException {
        connectionResult.waitForConnectionResult();
    }

    @Override
    public OnConnectedResult onConnected(AdvancedChat<MessageFromTwitch> chat, int nbTries) {
        try {
            final var cap = new Cap(Capability.AllCapabilities());

            final var pass = new Pass(oAuthData.getAccessToken(OAuthAccessMode.USER_ONLY));
            final var nick = new Nick(oAuthData.getLogin());

            LOG.info("Send PASS and NICK to connect to Twitch Chat (nick='"+nick+"'");
            final var receipt = chat.sendRequest(cap)
                                    .thenCompose(r -> chat.sendCommand(pass))
                                    .thenCompose(v -> chat.sendRequest(nick))
                                    .thenApply(ReceiptSlip::getAnswer)
                                    .toCompletableFuture()
                                    .get();

            LOG.info("{} is connected to chat", oAuthData.getLogin());

            connectionResult.setOnConnection(chat);
            final var listener = new Listener();
            listener.onChatEvent(new Connection<>());
            subscriptionHolder.replace(() -> chat.addChatListener(listener));
            return OnConnectedResult.OK;
        } catch (Throwable e) {
            if (isCausedByAnInvalidToken(e)) {
                try {
                    LOG.warn("Fail to connect. Retry after refreshing the token");
                    oAuthData.refresh(OAuthAccessMode.USER_ONLY);
                } catch (Throwable t) {
                    LOG.error("Fail to refresh token",t);
                    return OnConnectedResult.FAILED;
                }
                return OnConnectedResult.RETRY;
            }
            return OnConnectedResult.FAILED;
        }
    }


    private boolean isCausedByAnInvalidToken(Throwable e) {
        if (e instanceof CipherException) {
            return true;
        }
        return e.getCause() instanceof TwitchChatAuthenticationFailure;
    }


    private class Listener implements AdvancedChatListener<MessageFromTwitch>, AdvancedChatEventVisitor<MessageFromTwitch, Nil> {

        @Override
        public void onChatEvent(AdvancedChatEvent<MessageFromTwitch> chatEvent) {
            chatEvent.accept(this);
        }

        @Override
        public Nil visit(Connection<MessageFromTwitch> event) {
            LOG.info("Connection event on Twitch Chat : {}", event);
            listeners.forEachListeners(TwitchChatListener::onConnected);
            return Nil.NULL;
        }

        @Override
        public Nil visit(Disconnection<MessageFromTwitch> event) {
            LOG.warn("Disconnection event on Twitch Chat : {}", event);
            listeners.forEachListeners(TwitchChatListener::onDisconnected);
            return Nil.NULL;
        }

        @Override
        public Nil visit(PostedMessage<MessageFromTwitch> event) {
            if (event.getPostedMessage() instanceof MessageToTwitch messageToTwitch) {
                listeners.forEachListeners(TwitchChatListener::onPostedMessage, event.dispatchingTime(), messageToTwitch);
            }
            return Nil.NULL;
        }

        @Override
        public Nil visit(ReceivedMessage<MessageFromTwitch> event) {
            if (event.getMessage() instanceof PingFromTwitch) {
                connectionResult.getChat().sendCommand(Pong.create());
            } else {
                listeners.forEachListeners(TwitchChatListener::onReceivedMessage, event.getReceptionTime(), event.getMessage());
            }
            return Nil.NULL;
        }

        @Override
        public Nil visit(Error<MessageFromTwitch> event) {
            LOG.warn("Error for Twitch Chat : {}", event);
            return Nil.NULL;
        }
    }
}
