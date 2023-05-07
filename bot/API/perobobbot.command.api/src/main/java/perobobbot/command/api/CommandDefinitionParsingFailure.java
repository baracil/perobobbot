package perobobbot.command.api;

import lombok.Getter;
import perobobbot.api.PerobobbotException;

public class CommandDefinitionParsingFailure extends PerobobbotException {

    @Getter
    private final String commandDefinition;

    public CommandDefinitionParsingFailure(String reason, String commandDefinition) {
        super("Could not parse definition '"+commandDefinition+"' : "+reason);
        this.commandDefinition = commandDefinition;
    }
}
