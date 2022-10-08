package perobobbot.service.api;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.api.data.EntityNotFound;
import perobobbot.api.data.Platform;

import java.util.List;

@Getter
public class NoBotDefined extends EntityNotFound {

    private final @NonNull Platform platform;

    public NoBotDefined(@NonNull Platform platform) {
        super("No Bot defined on platform '"+platform.name()+"'");
        this.platform = platform;
    }

    @Override
    public @NonNull List<Criteria> searchedCriteria() {
        return List.of(new Criteria("platform",platform));
    }
}
