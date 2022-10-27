package perobobbot.command._private.parser;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.command.CommandDefinitionParsingFailure;
import perobobbot.command._private.Command;
import perobobbot.command._private.CommandParser;

import java.util.regex.Pattern;

public enum FullMatchCommandParser implements CommandParser {
    INSTANCE,
    ;

    public static @NonNull FullMatchCommandParser create() {
        return INSTANCE;
    }

    public static final Pattern FULL_MATCH_COMMAND = Pattern.compile("^(?<command>[a-zA-Z0-9_]+)\\*$");

    @Override
    public @NonNull Command parse(@NonNull String commandDefinition) {
        final var match = FULL_MATCH_COMMAND.matcher(commandDefinition);
        if (!match.matches()) {
            throw new CommandDefinitionParsingFailure("No a full match command",commandDefinition);
        }

        final var command = match.group("command");
        final var regex =  "^%s( .*)?$".formatted(command);
        return new Command(commandDefinition, regex, command, command, ImmutableSet.of());
    }
}
