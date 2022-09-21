package perobobbot.api.data;

import lombok.Getter;
import lombok.NonNull;

public class ApplicationForPlatformMissing extends EntityNotFound {

    @Getter
    private final @NonNull Platform platform;

    public ApplicationForPlatformMissing(@NonNull Platform platform) {
        super("No application for the platform '"+ platform.name() +"' exists.");
        this.platform = platform;
    }

    @Override
    public @NonNull String searchedCriteria() {
        return "platform";
    }

    @Override
    public @NonNull String searchedValue() {
        return platform.name();
    }
}
