package perobobbot.api.data;

import lombok.Getter;
import lombok.NonNull;

import java.util.List;

public class NoMainUserToken extends EntityNotFound {

    @Getter
    private final @NonNull Platform platform;

    public NoMainUserToken(@NonNull Platform platform) {
        super("No main user token is defined for platform '"+platform+"'");
        this.platform = platform;
    }

    @Override
    public @NonNull List<Criteria> searchedCriteria() {
        return List.of(new Criteria("platform",platform));
    }
}
