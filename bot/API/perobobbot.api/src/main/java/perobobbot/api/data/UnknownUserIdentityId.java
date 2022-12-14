package perobobbot.api.data;

import lombok.Getter;
import lombok.NonNull;

import java.util.List;

@Getter
public class UnknownUserIdentityId extends EntityNotFound {

    private final long userIdentityId;

    public UnknownUserIdentityId(long userIdentityId) {
        super("No UserIdentity exists with id "+userIdentityId);
        this.userIdentityId = userIdentityId;
    }

    @Override
    public @NonNull List<Criteria> searchedCriteria() {
        return List.of(new Criteria("userIdentityId",userIdentityId));
    }
}
