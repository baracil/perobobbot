package perobobbot.api.data;

import io.micronaut.core.annotation.Introspected;
import lombok.NonNull;

import java.util.List;

public abstract class EntityNotFound extends DataException {

    public EntityNotFound(String message) {
        super(message);
    }

    public abstract @NonNull List<Criteria> searchedCriteria();

    @Introspected
    public record Criteria(@NonNull String key, @NonNull Object value) { }

}
