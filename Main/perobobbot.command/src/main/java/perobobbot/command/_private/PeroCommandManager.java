package perobobbot.command._private;

import fpc.tools.fp.Consumer1;
import fpc.tools.lang.Subscription;
import lombok.NonNull;
import lombok.Synchronized;
import perobobbot.api.data.view.UserIdentity;
import perobobbot.command.CommandBinding;
import perobobbot.command.CommandManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class PeroCommandManager implements CommandManager {

    private final @NonNull CommandParser parser = CommandParser.chain(CommandParser.fullMatch(), CommandParser.regexp());
    private final Map<String, Set<CommandData>> commands = new HashMap<>();

    @Override
    @Synchronized
    public @NonNull Subscription addCommand(@NonNull String commandDefinition,
                                            @NonNull Consumer1<? super CommandBinding> execution) {
        final var command = parser.parse(commandDefinition);
        final var data = new CommandData(command, execution);

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

    @Override
    @Synchronized
    public void handleMessage(@NonNull UserIdentity userIdentity,  @NonNull String message) {
        final var name = extractName(message.trim());
        final var set = commands.get(name);
        if (set == null) {
            return;
        }
        for (CommandData commandData : set) {
            if (commandData.handle(message)) {
                break;
            }
        }

    }

    private @NonNull String extractName(@NonNull String message) {
        final var idx = message.indexOf(" ");
        if (idx < 0) {
            return message;
        }
        return message.substring(0, idx);
    }

}
