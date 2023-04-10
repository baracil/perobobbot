package perobobbot.bridge;

import fpc.tools.lang.SubscriptionHolder;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Singleton;
import lombok.NonNull;
import perobobbot.bus.api.Bus;
import perobobbot.chat.api.ChatEvent;
import perobobbot.chat.api.PrivateChatMessage;
import perobobbot.command.api.CommandContext;
import perobobbot.command.api.CommandManager;

@Singleton
public class ChatToCommandBridge {

    private final @NonNull CommandManager commandManager;
//    private final @NonNull Producer producer;

    private final SubscriptionHolder subscriptionHolder = new SubscriptionHolder();

    public ChatToCommandBridge(@NonNull CommandManager commandManager, @NonNull Bus bus) {
        this.commandManager = commandManager;
//        this.producer = bus.createConsumer("chat:\\w+/[^$].+", ChatEvent.class);
    }

    @PostConstruct
    public void connectChatToCommandManager() {
//        subscriptionHolder.replace(() -> bus.addListener("chat:\\w+/[^$].+", ChatEvent.class, this::onBusEvent));
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
