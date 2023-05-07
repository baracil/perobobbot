package perobobbot.command.api;

public interface CommandManager extends CommandRegistry {

    String TRIGGER_COMMAND_TOPIC = "perobobbot:command/triggered";

    int VERSION = 1;

    void handleMessage(CommandContext context, String message);
}
