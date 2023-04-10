package perobobbot.command.impl;

import fpc.tools.lang.Subscription;
import fpc.tools.micronaut.EagerInit;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Singleton;
import lombok.NonNull;
import lombok.Synchronized;
import perobobbot.api.data.Platform;
import perobobbot.api.plugin.PerobobbotService;
import perobobbot.bus.api.Bus;
import perobobbot.bus.api.Producer;
import perobobbot.command.api.CommandContext;
import perobobbot.command.api.CommandManager;
import perobobbot.command.api.CommandRegistry;

import java.util.*;

@Singleton
@EagerInit
@PerobobbotService(serviceType = CommandRegistry.class, apiVersion = CommandManager.VERSION)
public class PeroCommandManager implements CommandManager {

    private final @NonNull Producer producer;
    private final @NonNull CommandParser parser = CommandParser.chain(CommandParser.fullMatch(), CommandParser.regexp());
    private final Map<String, Set<CommandData>> commands = new HashMap<>();

    public PeroCommandManager(@NonNull Bus bus) {
        this.producer = bus.createProducer(CommandManager.TRIGGER_COMMAND_TOPIC);
    }

    @Override
    @Synchronized
    public @NonNull Subscription addCommand(@NonNull String commandDefinition) {
        final var command = parser.parse(commandDefinition);
        final var data = new CommandData(command);

        commands.computeIfAbsent(command.getName(), n -> new TreeSet<>()).add(data);

        return () -> removeCommand(data);
    }

    @PreDestroy
    public void preDestroy() {
        producer.close();
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
                      .ifPresent(producer::send);
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
