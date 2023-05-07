package perobobbot.command.api;

import fpc.tools.fp.TryResult;
import fpc.tools.lang.Parser;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Value;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Serdeable
@Value
public class CommandBinding {

    /**
     * @return the definition of the command associated with this binding
     */
    String commandDefinition;
    /**
     * @return the part of the command will all parameters
     */
    String fullParameters;
    Map<String,String> parameters;


    public int getNumberOfParameters() {
        return parameters.size();
    }

    public Set<String> getParameterNames() {
        return parameters.keySet();
    }

    public Optional<String> findParameterValue(String name) {
        return Optional.ofNullable(parameters.get(name));
    }

    public String getParameterValue(String name) {
        return findParameterValue(name).orElseThrow(() -> new UnknownParameter(getCommandDefinition(), name));
    }

    public <T> Optional<T> findParameterValue(String name, Parser<T> parser) {
        return findParameterValue(name).map(parser::parse).flatMap(TryResult::getResult);
    }

    public <T> T getParameterValue(String name, Parser<T> parser) {
        return parser.parse(getParameterValue(name)).throwIfFailure();
    }

}
