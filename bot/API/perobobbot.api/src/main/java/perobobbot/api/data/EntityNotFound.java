package perobobbot.api.data;

import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;

import java.util.List;

public abstract class EntityNotFound extends DataException {

    public EntityNotFound(String message) {
        super(message);
    }

    public abstract @NonNull List<Criteria> searchedCriteria();

    @Serdeable
    public record Criteria(@NonNull String key, @NonNull Object value) { }

}
