package perobobbot.bom.generator;

import com.google.common.collect.ImmutableSet;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Bom {

    @Getter
    private final @NonNull ImmutableSet<Dependency> dependencies;
}
