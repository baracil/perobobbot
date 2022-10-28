package perobobbot.command.api;

import com.google.common.collect.ImmutableSet;
import fpc.tools.fp.TryResult;
import fpc.tools.lang.Parser;
import lombok.NonNull;

import java.util.Optional;

public interface CommandBinding {

    @NonNull String getCommandDefinition();

    @NonNull String getFullParameters();

    @NonNull ImmutableSet<String> getParameterNames();

    @NonNull Optional<String> findParameter(@NonNull String name);

    default int getNumberOfParameters() {
        return getParameterNames().size();
    }

    default @NonNull String getParameter(@NonNull String name) {
        return findParameter(name).orElseThrow(() -> new UnknownParameter(getCommandDefinition(), name));
    }

    default <T> @NonNull Optional<T> findParameter(@NonNull String name, @NonNull Parser<T> parser) {
        return findParameter(name).map(parser::parse).flatMap(TryResult::getResult);
    }

    default <T> @NonNull T getParameter(@NonNull String name, @NonNull Parser<T> parser) {
        return parser.parse(getParameter(name)).throwIfFailure();
    }

}
