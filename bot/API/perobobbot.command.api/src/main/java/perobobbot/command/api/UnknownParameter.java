package perobobbot.command.api;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class UnknownParameter extends CommandException {

    private final @NonNull String commandName;

    private final @NonNull String parameterName;

    public UnknownParameter(@NonNull String commandName, @NonNull String parameterName) {
        super("No parameter named '"+parameterName+"' for command '"+commandName+"'");
        this.commandName = commandName;
        this.parameterName = parameterName;
    }
}
