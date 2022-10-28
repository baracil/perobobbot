package perobobbot.command.api;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.api.PerobobbotException;

public class CommandDefinitionParsingFailure extends PerobobbotException {

    @Getter
    private final @NonNull String commandDefinition;

    public CommandDefinitionParsingFailure(@NonNull String reason, @NonNull String commandDefinition) {
        super("Could not parse definition '"+commandDefinition+"' : "+reason);
        this.commandDefinition = commandDefinition;
    }
}
