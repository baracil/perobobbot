package perobobbot.api;

import lombok.NonNull;
import lombok.Value;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public interface Scope {

    @NonNull String getName();

    static @NonNull String joinScopeNames(@NonNull Collection<? extends Scope> scopes, char delimiter) {
        return scopes.stream().map(Scope::getName).collect(Collectors.joining("" + delimiter));
    }

    static Set<Scope> splitScopes(@NonNull String scopeNames, char delimiter) {
        return Arrays.stream(scopeNames.split("" + delimiter))
                     .map(SimpleScope::new)
                     .collect(Collectors.toSet());
    }


    @Value
    class SimpleScope implements Scope {
        @NonNull String name;
    }
}
