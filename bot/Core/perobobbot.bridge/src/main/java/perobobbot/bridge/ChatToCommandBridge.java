package perobobbot.bridge;

import fpc.tools.lang.SubscriptionHolder;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Singleton;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.api.bus.Bus;
import perobobbot.chat.api.ChatEvent;
import perobobbot.chat.api.PrivateChatMessage;
import perobobbot.command.api.CommandContext;
import perobobbot.command.api.CommandManager;

@Singleton
@RequiredArgsConstructor
public class ChatToCommandBridge {

    private final @NonNull CommandManager commandManager;
    private final @NonNull Bus bus;

    private final SubscriptionHolder subscriptionHolder = new SubscriptionHolder();

    @PostConstruct
    public void connectChatToCommandManager() {
        subscriptionHolder.replace(() -> bus.addListener("chat:\\w+/[^$].+", ChatEvent.class, this::onBusEvent));
    }

    @PreDestroy
    public void disconnectChatFromCommandManager() {
        subscriptionHolder.unsubscribe();
    }

    private void onBusEvent(@NonNull ChatEvent e) {
        if (e instanceof PrivateChatMessage message) {
            final var context = createCommandContext(message);
            commandManager.handleMessage(context, message.getPrivateMessage());
        }
    }

    private CommandContext createCommandContext(PrivateChatMessage message) {
        return new CommandContext(message.getReceptionInstant(), message.getBotId(), message.getOwner(), message.getChannelName());
    }


}
