package perobobbot.api;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.Value;

import java.util.Arrays;
import java.util.stream.Collectors;

public interface Scope {

    @NonNull String getName();

    static @NonNull String joinScopeNames(@NonNull ImmutableCollection<? extends Scope> scopes, char delimiter) {
        return scopes.stream().map(Scope::getName).collect(Collectors.joining("" + delimiter));
    }

    static ImmutableSet<Scope> splitScopes(@NonNull String scopeNames, char delimiter) {
        return Arrays.stream(scopeNames.split("" + delimiter))
                     .map(SimpleScope::new)
                     .distinct()
                     .collect(ImmutableSet.toImmutableSet());
    }


    @Value
    class SimpleScope implements Scope {
        @NonNull String name;
    }
}
