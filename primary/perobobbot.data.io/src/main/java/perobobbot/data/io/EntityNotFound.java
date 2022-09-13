package perobobbot.data.io;

import lombok.NonNull;

public abstract class EntityNotFound extends DataException {

    public EntityNotFound(String message) {
        super(message);
    }

    public abstract @NonNull String searchedCriteria();
    public abstract @NonNull String searchedValue();

}
