package perobobbot.command.api;

import lombok.Getter;

@Getter
public class UnknownParameter extends CommandException {

    private final String commandName;

    private final String parameterName;

    public UnknownParameter(String commandName, String parameterName) {
        super("No parameter named '"+parameterName+"' for command '"+commandName+"'");
        this.commandName = commandName;
        this.parameterName = parameterName;
    }
}
