package perobobbot.chat.impl;

import fpc.tools.fp.Nil;
import fpc.tools.lang.SetTool;
import fpc.tools.lang.ThrowableTool;
import fpc.tools.micronaut.EagerInit;
import io.micronaut.scheduling.annotation.Scheduled;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Singleton;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import perobobbot.api.Identity;
import perobobbot.api.plugin.PerobobbotService;
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
@PerobobbotService(serviceType = ChatManager.class, apiVersion = ChatManager.VERSION)
public class DefaultChatManager implements ChatManager {

    private final UserIdentityService userIdentityService;
    private final ChatFactory chatFactory;

    private final Map<Identity, Chat> chats = new HashMap<>();

    public DefaultChatManager(
            UserIdentityService userIdentityService,
            List<PlatformChatFactory> platformChatFactories
    ) {
        this.userIdentityService = userIdentityService;
        this.chatFactory = DefaultChatFactory.create(platformChatFactories);
    }

    @Override
    @Synchronized
    public Optional<ChatIO> findChat(Identity identity) {
        return Optional.ofNullable(chats.get(identity));
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
        final var newBots = SetTool.difference(bots.keySet(), chats.keySet());
        final var oldBots = SetTool.difference(chats.keySet(), bots.keySet());

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
