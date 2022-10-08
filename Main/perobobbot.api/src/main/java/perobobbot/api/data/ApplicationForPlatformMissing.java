package perobobbot.api.data;

import lombok.Getter;
import lombok.NonNull;

import java.util.List;

public class ApplicationForPlatformMissing extends EntityNotFound {

    @Getter
    private final @NonNull Platform platform;

    public ApplicationForPlatformMissing(@NonNull Platform platform) {
        super("No application for the platform '"+ platform.name() +"' exists.");
        this.platform = platform;
    }


    @Override
    public @NonNull List<Criteria> searchedCriteria() {
        return List.of(new Criteria("platform",platform));
    }
}
