package perobobbot.chat.impl;

import com.google.common.collect.Sets;
import fpc.tools.lang.LoopAction;
import fpc.tools.lang.Looper;
import fpc.tools.lang.ThrowableTool;
import fpc.tools.micronaut.EagerInit;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Singleton;
import lombok.NonNull;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import perobobbot.api.Identification;
import perobobbot.api.data.UserIdentity;
import perobobbot.chat.api.*;
import perobobbot.service.api.UserIdentityService;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Singleton
@Slf4j
@EagerInit
public class DefaultChatManager implements ChatManager {

    private final @NonNull UserIdentityService userIdentityService;
    private final @NonNull ChatFactory chatFactory;

    private final Map<Identification, Chat> chats = new HashMap<>();

    private final Lock lock = new ReentrantLock();
    private final Looper looper = Looper.scheduled(new ConnectionAction(), Duration.ofSeconds(10));

    public DefaultChatManager(
            @NonNull UserIdentityService userIdentityService,
            @NonNull List<PlatformChatFactory> platformChatFactories
            ) {
        this.userIdentityService = userIdentityService;
        this.chatFactory = DefaultChatFactory.create(platformChatFactories);
    }

    @Override
    public @NonNull Optional<ChatIO> findChat(@NonNull UserIdentity userIdentity) {
        lock.lock();
        try {
            return Optional.ofNullable(chats.get(userIdentity.identification()));
        } finally {
            lock.unlock();
        }
    }

    @PostConstruct
    @Synchronized
    public void start() {
        looper.start();
    }

    @PreDestroy
    @Synchronized
    public void stop() {
        looper.requestStop();
    }

    private class ConnectionAction implements LoopAction {

        @Override
        public @NonNull NextState performOneIteration() throws Throwable {
            lock.lock();
            try {
                perform();
            } finally {
                lock.unlock();
            }
            return NextState.CONTINUE;
        }

        private void perform() {
            final var bots = userIdentityService.findBots();
            final var newBots = Sets.difference(bots.keySet(),chats.keySet());
            final var oldBots = Sets.difference(chats.keySet(),bots.keySet());

            for (Identification oldBot : oldBots) {
                final var chat = chats.remove(oldBot);
                if (chat != null) {
                    LOG.info("Disconnect {} from chat",oldBot);
                    chat.requestDisconnection();
                }
            }

            for (Identification newBot : newBots) {
                final var botIdentity = bots.get(newBot);
                try {
                    if (botIdentity != null) {
                        final var chat = chatFactory.create(botIdentity);
                        chats.put(newBot,chat);
                    }
                } catch (Exception e) {
                    ThrowableTool.interruptIfCausedByInterruption(e);
                    LOG.warn("Could not start chat for {}",botIdentity,e);
                }
            }

        }

        @Override
        public boolean shouldStopOnError(@NonNull Throwable error) {
            return false;
        }
    }
}
