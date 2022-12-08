package perobobbot.command.impl;

import fpc.tools.lang.Subscription;
import fpc.tools.lang.SubscriptionHolder;
import fpc.tools.micronaut.EagerInit;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Singleton;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import perobobbot.api.bus.Bus;
import perobobbot.api.data.Platform;
import perobobbot.api.plugin.PerobobbotService;
import perobobbot.chat.api.ChatEvent;
import perobobbot.chat.api.PrivateChatMessage;
import perobobbot.command.api.CommandContext;
import perobobbot.command.api.CommandManager;
import perobobbot.command.api.CommandRegistry;

import java.util.*;

@Singleton
@RequiredArgsConstructor
@EagerInit
@PerobobbotService(serviceType = CommandRegistry.class, apiVersion = CommandManager.VERSION)
public class PeroCommandManager implements CommandManager {

    private final @NonNull Bus bus;
    private final @NonNull CommandParser parser = CommandParser.chain(CommandParser.fullMatch(), CommandParser.regexp());
    private final Map<String, Set<CommandData>> commands = new HashMap<>();
    private final SubscriptionHolder subscriptionHolder = new SubscriptionHolder();


    @PostConstruct
    public void registerToBus() {
        subscriptionHolder.replace(() -> bus.addListener("chat:\\w+/[^$].+",ChatEvent.class, this::onBusEvent));
    }

    @PreDestroy
    public void unregisterFromBus() {
        subscriptionHolder.unsubscribe();
    }

    @Override
    @Synchronized
    public @NonNull Subscription addCommand(@NonNull String commandDefinition) {
        final var command = parser.parse(commandDefinition);
        final var data = new CommandData(command);

        commands.computeIfAbsent(command.getName(), n -> new TreeSet<>()).add(data);

        return () -> removeCommand(data);
    }

    @Synchronized
    private void removeCommand(@NonNull CommandData command) {
        final var set = commands.get(command.getCommandName());
        if (set == null) {
            return;
        }

        set.remove(command);

        if (set.isEmpty()) {
            commands.remove(command.getCommandName());
        }
    }

    //TODO move this to a specific class that makes the bridge between chat->commandManager
    public void onBusEvent(ChatEvent e) {
        if (e instanceof PrivateChatMessage message) {
            final var context = new CommandContext(message.getReceptionInstant(), message.getBotId(), message.getOwner(), message.getChannelName());
            handleMessage(context, message.getPrivateMessage());
        }
    }

    @Override
    public void handleMessage(@NonNull CommandContext context, @NonNull String message) {
        final var preparedMessage = prepareMessage(context.getPlatform(), message);
        if (preparedMessage == null) {
            return;
        }
        final var name = extractCommandName(preparedMessage);
        final var commandDataSet = commands.get(name);
        if (commandDataSet == null) {
            return;
        }
        commandDataSet.stream()
                      .map(d -> d.handle(context, preparedMessage))
                      .flatMap(Optional::stream)
                      .findFirst()
                      .ifPresent(event -> bus.publishEvent(CommandManager.TRIGGER_COMMAND_TOPIC,event));
    }

    private String prepareMessage(@NonNull Platform platform, @NonNull String message) {
        final var trimmed = message.trim();
        return switch (platform.name()) {
            case "TWITCH" -> trimmed.startsWith("!") ? trimmed.substring(1) : null;
            default -> null;
        };
    }


    private @NonNull String extractCommandName(@NonNull String message) {
        final var idx = message.indexOf(" ");
        if (idx < 0) {
            return message;
        }
        return message.substring(0, idx);
    }

}
