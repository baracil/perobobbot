package perobobbot.command.api;

public interface CommandAction {

    void execute(CommandContext context, CommandBinding commandBinding);
}
