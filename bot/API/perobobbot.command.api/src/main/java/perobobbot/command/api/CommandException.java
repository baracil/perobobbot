package perobobbot.command.api;


import perobobbot.api.PerobobbotException;

public class CommandException extends PerobobbotException {

    public CommandException(String message) {
        super(message);
    }

    public CommandException(String message, Throwable cause) {
        super(message, cause);
    }
}
