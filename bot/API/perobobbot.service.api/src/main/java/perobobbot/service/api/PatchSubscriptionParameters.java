package perobobbot.service.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.micronaut.core.annotation.Nullable;
import lombok.NonNull;
import lombok.Value;

import java.util.Optional;

@Value
public class PatchSubscriptionParameters {

    @Nullable Boolean enabled;

    @JsonIgnore
    public @NonNull Optional<Boolean> getEnabled() {
        return Optional.ofNullable(enabled);
    }
}
