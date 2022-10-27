package perobobbot.command._private;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import perobobbot.command._private.parser.FullMatchCommandParser;
import perobobbot.command._private.parser.RegexpCommandParser;

public interface CommandParser {

    /**
     * @param commandDefinition the definition of the command
     * @return the parsed command that can be used to create binding
     */
    @NonNull Command parse(@NonNull String commandDefinition);


    static @NonNull CommandParser chain(@NonNull CommandParser... parsers) {
        return new ChainedCommandParser(ImmutableList.copyOf(parsers));
    }

    static @NonNull CommandParser regexp() {
        return RegexpCommandParser.create();
    }

    static @NonNull CommandParser fullMatch() {
        return FullMatchCommandParser.create();
    }
}
