package perobobbot.command.impl.parser;

import perobobbot.command.api.CommandDefinitionParsingFailure;
import perobobbot.command.impl.Command;
import perobobbot.command.impl.CommandParser;

import java.util.Set;
import java.util.regex.Pattern;

public enum FullMatchCommandParser implements CommandParser {
    INSTANCE,
    ;

    public static FullMatchCommandParser create() {
        return INSTANCE;
    }

    public static final Pattern FULL_MATCH_COMMAND = Pattern.compile("^(?<command>[a-zA-Z0-9_]+)\\*$");

    @Override
    public Command parse(String commandDefinition) {
        final var match = FULL_MATCH_COMMAND.matcher(commandDefinition);
        if (!match.matches()) {
            throw new CommandDefinitionParsingFailure("No a full match command",commandDefinition);
        }

        final var command = match.group("command");
        final var regex =  "^%s( .*)?$".formatted(command);
        return new Command(commandDefinition, regex, command, command, Set.of());
    }
}
