package perobobbot.command.api;

import fpc.tools.fp.TryResult;
import fpc.tools.lang.Parser;

import java.util.Optional;
import java.util.Set;

public interface CommandBinding2 {

    /**
     * @return the definition of the command associated with this binding
     */
    String getCommandDefinition();

    /**
     * @return the part of the command will all parameters
     */
    String getFullParameters();

    Set<String> getParameterNames();

    Optional<String> findParameterValue(String name);

    default int getNumberOfParameters() {
        return getParameterNames().size();
    }

    default String getParameterValue(String name) {
        return findParameterValue(name).orElseThrow(() -> new UnknownParameter(getCommandDefinition(), name));
    }

    default <T> Optional<T> findParameterValue(String name, Parser<T> parser) {
        return findParameterValue(name).map(parser::parse).flatMap(TryResult::getResult);
    }

    default <T> T getParameterValue(String name, Parser<T> parser) {
        return parser.parse(getParameterValue(name)).throwIfFailure();
    }

}
