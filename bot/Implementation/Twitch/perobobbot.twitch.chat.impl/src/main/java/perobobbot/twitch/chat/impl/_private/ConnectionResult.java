package perobobbot.twitch.chat.impl._private;

import fpc.tools.advanced.chat.AdvancedChat;
import fpc.tools.chat.ChatNotConnected;
import fpc.tools.lang.SmartLock;
import lombok.RequiredArgsConstructor;
import perobobbot.twitch.chat.message.from.MessageFromTwitch;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.function.UnaryOperator;

public class ConnectionResult {
    private final SmartLock lock = SmartLock.reentrant();
    private final Condition connectionResultChanged = lock.newCondition();

    private final AtomicReference<Data> connectionResult = new AtomicReference<>(null);

    public AdvancedChat<MessageFromTwitch> getChat() {
        final var data = connectionResult.get();
        if (data == null) {
            throw new ChatNotConnected();
        }
        return data.getChat();
    }

    public void waitForConnectionResult() throws InterruptedException {
        lock.lock();
        try {
            while (connectionResult.get() == null) {
                connectionResultChanged.await();
            }
            connectionResult.get().throwIfNotConnected();
        } finally {
            lock.unlock();
        }
    }

    public void setOnConnection(AdvancedChat<MessageFromTwitch> chat) {
        this.setConnectionResult(m -> new Data(chat,null));
    }

    public void setOnConnectionFailed(RuntimeException error) {
        this.setConnectionResult(m -> new Data(null,error));
    }

    public void setOnDisconnection() {
        this.setConnectionResult(m -> {
            if (m == null) {
                return new Data(null,null);
            }
            return m.disconnected();
        });
    }

    public void clearConnectionResult() {
        this.setConnectionResult(m -> null);
    }

    private void setConnectionResult(UnaryOperator<Data> mutator) {
        lock.lock();
        try {
            final var a = connectionResult.updateAndGet(mutator);
            connectionResultChanged.signalAll();
        } finally {
            lock.unlock();
        }
    }


    public boolean isRunning() {
        final var c = connectionResult.get();
        return c != null && c.isRunning();
    }

    public void throwIfConnectionFailed() {
        final var r = connectionResult.get();
        if (r == null) {
            throw new ChatNotConnected();
        }
        r.throwIfNotConnected();
    }

    @RequiredArgsConstructor
    private static class Data {
        final AdvancedChat<MessageFromTwitch> chat;
        final RuntimeException error;

        public boolean isRunning() {
            return chat != null && chat.isRunning();
        }

        public AdvancedChat<MessageFromTwitch> getChat() {
            if (error != null) {
                throw error;
            }
            if (chat == null) {
                throw new ChatNotConnected();
            }
            return chat;
        }

        public Data disconnected() {
            if (error != null) {
                return this;
            }
            return new Data(null,null);
        }

        public void throwIfNotConnected() {
            getChat();
        }
    }
}
