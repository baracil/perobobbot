package perobobobbot.command;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import perobobbot.command.api.CommandDefinitionParsingFailure;
import perobobbot.command.impl.CommandParser;
import perobobbot.command.impl.parser.RegexpCommandParser;

import java.util.stream.Stream;

public class CommandBindingDefinitionRegexpParserTest {

    private static String requiredParameter(String name) {
        return requiredParameter(name,false);
    }

    private static String requiredParameter(String name,boolean noSpace) {
        final var base ="(?<"+name+">"+ RegexpCommandParser.ARGUMENT_PATTERN+")";
        return noSpace?base:"\s+"+base;
    }
    private static String optionalParameter(String name) {
        final var base ="(?<"+name+">"+ RegexpCommandParser.ARGUMENT_PATTERN+")";
        return "("+("\s+"+base)+")?";
    }

    public static Stream<Arguments> validSamples() {
        return Stream.of(
                Arguments.of("play?", "play?",0, "^play\\?$"),
                Arguments.of("decho*", "decho",0, "^decho( .*)?$"),
                Arguments.of("repeat*", "repeat",0, "^repeat( .*)?$"),
                Arguments.of("play stop", "play|stop",0, "^play\s+stop$"),
                Arguments.of("play {title}", "play", 1,"^play("+requiredParameter("title")+")$"),
                Arguments.of("play {title} [volume]","play",2,"^play("+requiredParameter("title")+optionalParameter("volume")+")$"),
                Arguments.of("em list","em|list",0,"^em\s+list$"),
                Arguments.of("em enable {extension}","em|enable",1,"^em\s+enable("+requiredParameter("extension")+")$"),
                Arguments.of("em.new enable {extension}","em.new|enable",1,"^em\\.new\s+enable("+requiredParameter("extension")+")$"),
                Arguments.of("play {x},{y} [arg3]","play",3,"^play("+requiredParameter("x")+","+requiredParameter("y",true)+optionalParameter("arg3")+")$")
        );
    }

    public static Stream<String> invalidSamples() {
        return Stream.of(
                "play {u a}",
                "u&",
                "play {title} {volum e}"
        );
    }

    private CommandParser parser;

    @BeforeEach
    public void setUp() {
        parser = CommandParser.chain(CommandParser.fullMatch(), CommandParser.regexp());
    }

    @ParameterizedTest
    @MethodSource("invalidSamples")
    public void shouldFail(String definition) {
        Assertions.assertThrows(CommandDefinitionParsingFailure.class,() -> parser.parse(definition));
    }

    @ParameterizedTest
    @MethodSource("validSamples")
    public void shouldHaveRightRegexp(String definition, @AggregateWith(ParsingAggregator.class) Parsing expected) {
        final var actual = parser.parse(definition);
        Assertions.assertEquals(expected.getRegexp(),actual.getRegexp().pattern());
    }

    @ParameterizedTest
    @MethodSource("validSamples")
    public void shouldHaveRightFullCommand(String definition, @AggregateWith(ParsingAggregator.class) Parsing expected) {
        final var actual = parser.parse(definition);
        Assertions.assertEquals(expected.getFullCommand(),actual.getFullCommand());
    }

    @ParameterizedTest
    @MethodSource("validSamples")
    public void shouldHaveRightNbParameters(String definition, @AggregateWith(ParsingAggregator.class) Parsing expected) {
        final var actual = parser.parse(definition);
        Assertions.assertEquals(expected.getNbParameters(),actual.getParameters().size());
    }

}
