package perobobbot.command.api;

import fpc.tools.fp.TryResult;
import fpc.tools.lang.Parser;
import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;
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
    @NonNull String commandDefinition;
    /**
     * @return the part of the command will all parameters
     */
    @NonNull String fullParameters;
    @NonNull Map<String,String> parameters;


    public int getNumberOfParameters() {
        return parameters.size();
    }

    public @NonNull Set<String> getParameterNames() {
        return parameters.keySet();
    }

    public @NonNull Optional<String> findParameterValue(@NonNull String name) {
        return Optional.ofNullable(parameters.get(name));
    }

    public @NonNull String getParameterValue(@NonNull String name) {
        return findParameterValue(name).orElseThrow(() -> new UnknownParameter(getCommandDefinition(), name));
    }

    public <T> @NonNull Optional<T> findParameterValue(@NonNull String name, @NonNull Parser<T> parser) {
        return findParameterValue(name).map(parser::parse).flatMap(TryResult::getResult);
    }

    public <T> @NonNull T getParameterValue(@NonNull String name, @NonNull Parser<T> parser) {
        return parser.parse(getParameterValue(name)).throwIfFailure();
    }

}
