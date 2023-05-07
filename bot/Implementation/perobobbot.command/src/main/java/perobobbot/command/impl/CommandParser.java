package perobobbot.command.impl;

import perobobbot.command.impl.parser.FullMatchCommandParser;
import perobobbot.command.impl.parser.RegexpCommandParser;

import java.util.List;

public interface CommandParser {

    /**
     * @param commandDefinition the definition of the command
     * @return the parsed command that can be used to create binding
     */
    Command parse(String commandDefinition);


    static CommandParser chain(CommandParser... parsers) {
        return new ChainedCommandParser(List.of(parsers));
    }

    static CommandParser regexp() {
        return RegexpCommandParser.create();
    }

    static CommandParser fullMatch() {
        return FullMatchCommandParser.create();
    }
}
