package perobobbot.command.api;

import com.google.common.collect.ImmutableSet;
import fpc.tools.fp.TryResult;
import fpc.tools.lang.Parser;
import lombok.NonNull;

import java.util.Optional;

public interface CommandBinding {

    /**
     * @return the definition of the command associated with this binding
     */
    @NonNull String getCommandDefinition();

    /**
     * @return the part of the command will all parameters
     */
    @NonNull String getFullParameters();

    @NonNull ImmutableSet<String> getParameterNames();

    @NonNull Optional<String> findParameterValue(@NonNull String name);

    default int getNumberOfParameters() {
        return getParameterNames().size();
    }

    default @NonNull String getParameterValue(@NonNull String name) {
        return findParameterValue(name).orElseThrow(() -> new UnknownParameter(getCommandDefinition(), name));
    }

    default <T> @NonNull Optional<T> findParameterValue(@NonNull String name, @NonNull Parser<T> parser) {
        return findParameterValue(name).map(parser::parse).flatMap(TryResult::getResult);
    }

    default <T> @NonNull T getParameterValue(@NonNull String name, @NonNull Parser<T> parser) {
        return parser.parse(getParameterValue(name)).throwIfFailure();
    }

}
