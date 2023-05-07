package perobobbot.command.impl.parser;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import perobobbot.command.api.CommandDefinitionParsingFailure;
import perobobbot.command.api.Parameter;
import perobobbot.command.impl.Command;
import perobobbot.command.impl.CommandParser;

import java.util.HashSet;
import java.util.Set;

@Slf4j
public enum RegexpCommandParser implements CommandParser {
    INSTANCE,
    ;

    public static RegexpCommandParser create() {
        return INSTANCE;
    }

    public Command parse(String commandDefinition) {
        return new Execution(commandDefinition.trim()).parse();
    }

//    public static final String ARGUMENT_SINGLE_QUOTE = "'(?:[^ '\\\\]|\\\\.)+'";
//    public static final String ARGUMENT_DOUBLE_QUOTE = "\"(?:[^ \"\\\\]|\\\\.)+\"";
    public static final String ARGUMENT_SINGLE_QUOTE = "'[^'\\\\]*(?:\\\\.[^'\\\\]*)*'";
    public static final String ARGUMENT_DOUBLE_QUOTE = "\"[^\"\\\\]*(?:\\\\.[^\"\\\\]*)*\"";
    public static final String ARGUMENT_NO_QUOTE = "[^ \"']+";


    public static final String ARGUMENT_PATTERN = "(" + ARGUMENT_SINGLE_QUOTE + "|" + ARGUMENT_DOUBLE_QUOTE + "|" + ARGUMENT_NO_QUOTE + ")";


    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    private static class Execution {

        private final String commandDefinition;

        private final StringBuilder regexpBuilder = new StringBuilder();

        private final Set<Parameter> parameterDefinitions = new HashSet<>();

        private final StringBuilder fullCommandBuilder = new StringBuilder();

        private int index = 0;

        private boolean inFullCommand = true;

        private boolean spacePending = false;

        private Command parse() {

            this.startPattern();
            while (index < commandDefinition.length()) {
                parseToken();
            }
            this.closePattern();
            final var fullCommand = this.getFullCommand();
            final var firstCommand = extractFirstCommand(fullCommand);
            return new Command(
                    commandDefinition,
                    regexpBuilder.toString(),
                    firstCommand,
                    fullCommand,
                    Set.copyOf(parameterDefinitions));
        }


        private String extractFirstCommand(String fullCommand) {
            final var idx = fullCommand.indexOf("|");
            if (idx < 0) {
                return fullCommand;
            }
            return fullCommand.substring(0, idx);
        }

        private String getFullCommand() {
            final var fullCommand = this.fullCommandBuilder.toString();
            if (!fullCommand.chars().allMatch(c -> c == '|' || isValidChar(c))) {
                throw createError("Command contains invalid character : '" + fullCommand + "'");
            }
            return fullCommand;
        }

        private void startPattern() {
            this.regexpBuilder.append("^");
        }

        private void closePattern() {
            if (!inFullCommand) {
                regexpBuilder.append(")");
            }
            this.regexpBuilder.append("$");
        }

        private void parseToken() {
            final char c = commandDefinition.charAt(index++);
            switch (c) {
                case '[', '{' -> {
                    if (inFullCommand) {
                        regexpBuilder.append("(");
                    }
                    inFullCommand = false;
                    parseParameter(c == '[');
                }
                case ' ' -> spacePending = true;
                default -> {
                    if (spacePending) {
                        if (inFullCommand) {
                            fullCommandBuilder.append("|");
                        }
                        regexpBuilder.append(" +");
                    }
                    if (inFullCommand) {
                        fullCommandBuilder.append(c);
                    }
                    appendChar(c);
                    spacePending = false;
                }
            }
        }

        private void appendChar(char c) {
            regexpBuilder.append(escapeChar(c));
        }

        private String escapeChar(char c) {
            return switch (c) {
                case '.', '*', '?', '-' -> "\\" + c;
                default -> String.valueOf(c);
            };
        }


        private void parseParameter(boolean optional) {
            final var parameterName = extractParameterName(optional ? ']' : '}');
            this.parameterDefinitions.add(new Parameter(parameterName, optional));
            regexpBuilder.append(optional ? optionalParameter(parameterName) : requiredParameter(parameterName));
            spacePending = false;
        }

        private String extractParameterName(char closingChar) {
            final int idx = commandDefinition.indexOf(closingChar, index);
            if (idx < 0) {
                throw createError("No closing char for parameter");
            }
            final var name = commandDefinition.substring(index, idx);
            if (!isValidParameterName(name)) {
                throw createError("Invalid parameter name '" + name + "'");
            }
            index = idx + 1;
            return name;
        }

        private boolean isValidParameterName(String name) {
            return (!name.isEmpty() && name.chars().allMatch(this::isValidChar));
        }

        private boolean isValidChar(int c) {
            return c == '-' || c == '.' || c == '?' || (c >= '0' && c <= '9') || (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z');
        }

        private String requiredParameter(String name) {
            final var base = "(?<" + name + ">" + RegexpCommandParser.ARGUMENT_PATTERN + ")";
            if (spacePending) {
                spacePending = false;
                return " +" + base;
            } else {
                return base;
            }
        }

        private String optionalParameter(String name) {
            final var base = "(?<" + name + ">" + RegexpCommandParser.ARGUMENT_PATTERN + ")";
            if (spacePending) {
                spacePending = false;
                return "( +" + base + ")?";
            } else {
                return base + "?";
            }
        }


        private CommandDefinitionParsingFailure createError(String reason) {
            return new CommandDefinitionParsingFailure(reason, commandDefinition);
        }
    }

}
