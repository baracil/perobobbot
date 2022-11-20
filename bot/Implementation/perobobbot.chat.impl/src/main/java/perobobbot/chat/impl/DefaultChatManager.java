package perobobbot.chat.impl;

import com.google.common.collect.Sets;
import fpc.tools.fp.Nil;
import fpc.tools.lang.ThrowableTool;
import fpc.tools.micronaut.EagerInit;
import io.micronaut.scheduling.annotation.Scheduled;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Singleton;
import lombok.NonNull;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import perobobbot.api.Identity;
import perobobbot.api.data.UserIdentity;
import perobobbot.chat.api.*;
import perobobbot.service.api.UserIdentityService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

@Singleton
@Slf4j
@EagerInit
public class DefaultChatManager implements ChatManager {

    private final @NonNull UserIdentityService userIdentityService;
    private final @NonNull ChatFactory chatFactory;

    private final Map<Identity, Chat> chats = new HashMap<>();

    public DefaultChatManager(
            @NonNull UserIdentityService userIdentityService,
            @NonNull List<PlatformChatFactory> platformChatFactories
    ) {
        this.userIdentityService = userIdentityService;
        this.chatFactory = DefaultChatFactory.create(platformChatFactories);
    }

    @Override
    @Synchronized
    public @NonNull Optional<ChatIO> findChat(@NonNull UserIdentity userIdentity) {
        return Optional.ofNullable(chats.get(userIdentity.identity()));
    }


    @Scheduled(fixedDelay = "10s", fixedRate = "10s")
    public void handleConnectionChanges() {
        try {
            manageChanges();
        } catch (Throwable t) {
            ThrowableTool.interruptIfCausedByInterruption(t);
            LOG.warn("Fail to handle connection changes", t);
        }
    }

    @PreDestroy
    @Synchronized
    public void disconnectAll() throws ExecutionException, InterruptedException {
        final var collect = chats.values()
                                 .stream()
                                 .<CompletionStage<?>>map(Chat::requestDisconnection)
                                 .reduce((c1, c2) -> c1.runAfterBoth(c2, () -> {}));
        collect.orElseGet(() -> CompletableFuture.completedFuture(Nil.NULL)).toCompletableFuture().get();
    }

    @Synchronized
    private void manageChanges() {
        final var bots = userIdentityService.findBots();
        final var newBots = Sets.difference(bots.keySet(), chats.keySet());
        final var oldBots = Sets.difference(chats.keySet(), bots.keySet());

        for (Identity oldBot : oldBots) {
            final var chat = chats.remove(oldBot);
            if (chat != null) {
                LOG.info("Disconnect {} from chat", oldBot);
                chat.requestDisconnection();
            }
        }

        for (Identity newBot : newBots) {
            final var botIdentity = bots.get(newBot);
            try {
                if (botIdentity != null) {
                    LOG.info("Connect {} to {} chat", botIdentity.name(),botIdentity.platform().name());
                    final var chat = chatFactory.create(botIdentity);
                    chats.put(newBot, chat);
                }
            } catch (Exception e) {
                ThrowableTool.interruptIfCausedByInterruption(e);
                LOG.warn("Could not start chat for {}", botIdentity, e);
            }
        }

    }
}
