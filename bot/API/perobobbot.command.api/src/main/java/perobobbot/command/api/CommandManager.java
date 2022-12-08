package perobobbot.command.api;

import lombok.NonNull;

public interface CommandManager extends CommandRegistry {

    String TRIGGER_COMMAND_TOPIC = "perobobbot:command/triggered";

    int VERSION = 1;

    void handleMessage(@NonNull CommandContext context, @NonNull String message);
}
