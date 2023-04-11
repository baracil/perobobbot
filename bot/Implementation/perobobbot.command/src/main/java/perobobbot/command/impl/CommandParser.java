package perobobbot.command.impl;

import lombok.NonNull;
import perobobbot.command.impl.parser.FullMatchCommandParser;
import perobobbot.command.impl.parser.RegexpCommandParser;

import java.util.List;

public interface CommandParser {

    /**
     * @param commandDefinition the definition of the command
     * @return the parsed command that can be used to create binding
     */
    @NonNull Command parse(@NonNull String commandDefinition);


    static @NonNull CommandParser chain(@NonNull CommandParser... parsers) {
        return new ChainedCommandParser(List.of(parsers));
    }

    static @NonNull CommandParser regexp() {
        return RegexpCommandParser.create();
    }

    static @NonNull CommandParser fullMatch() {
        return FullMatchCommandParser.create();
    }
}
