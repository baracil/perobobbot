package perobobbot.api.data;

import io.micronaut.core.annotation.Introspected;

import java.util.List;

public abstract class EntityNotFound extends DataException {

    public EntityNotFound(String message) {
        super(message);
    }

    public abstract List<Criteria> searchedCriteria();

    @Introspected
    public record Criteria(String key, Object value) { }

}
