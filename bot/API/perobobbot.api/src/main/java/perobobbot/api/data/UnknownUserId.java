package perobobbot.api.data;

import lombok.Getter;
import perobobbot.api.Id;

import java.util.List;

public class UnknownUserId extends EntityNotFound {

    @Getter
    private final Id id;

    public UnknownUserId(Id id) {
        super("No UserIdentity with id "+id);
        this.id = id;
    }

    @Override
    public List<Criteria> searchedCriteria() {
        return List.of(new Criteria("id",id));
    }
}
